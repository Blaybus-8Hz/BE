package com.haertz.be.booking.service;

import com.haertz.be.booking.adaptor.DesignerScheduleAdaptor;
import com.haertz.be.booking.constant.AvailableTimeSlots;
import com.haertz.be.booking.converter.BookingConverter;
import com.haertz.be.booking.dto.request.PreBookingRequest;
import com.haertz.be.booking.entity.DesignerSchedule;
import com.haertz.be.booking.exception.BookingErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.payment.entity.PaymentStatus;
import com.haertz.be.payment.exception.PaymentErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import static com.haertz.be.booking.constant.AvailableTimeSlots.TIME_SLOTS;
import static com.haertz.be.common.constant.StaticValue.MAX_SLOTS;

@Service
@RequiredArgsConstructor
public class DesignerScheduleDomainService {
    private final DesignerScheduleAdaptor designerScheduleAdaptor;
    @Transactional
    public Long registerTempSchedule(Long userId, PreBookingRequest preBooking) {
        if (designerScheduleAdaptor.hasBookingByTimeSlot(preBooking.designerId(), preBooking.bookingDate(), preBooking.bookingTime())) {
            throw new BaseException(BookingErrorCode.BOOKING_ALREADY_REGISTERED);
        }

        validateBookingDateTime(preBooking);
        DesignerSchedule designerSchedule = BookingConverter.toDesignerSchedule(userId, preBooking, PaymentStatus.IN_PROGRESS);
        designerScheduleAdaptor.save(designerSchedule);

        return designerSchedule.getDesignerScheduleId();
    }
    @Transactional
    public void confirmScheduleAfterPayment(Long designerScheduleId, PaymentStatus paymentStatus) {
        DesignerSchedule designerSchedule = designerScheduleAdaptor.findById(designerScheduleId);

        if (paymentStatus != PaymentStatus.PENDING && paymentStatus != PaymentStatus.COMPLETED) {
            throw new BaseException(PaymentErrorCode.PAYMENT_STATUS_INVALID);
        }

        if (designerSchedule.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new BaseException(PaymentErrorCode.PAYMENT_ALREADY_COMPLETED);
        }

        designerSchedule.markAsCompleted(paymentStatus);
        designerScheduleAdaptor.save(designerSchedule);
    }

    @Transactional
    public void deleteScheduleAfterFailedPayment(Long designerScheduleId) {
        designerScheduleAdaptor.deleteById(designerScheduleId);
    }

    private void validateBookingDateTime(PreBookingRequest preBookingRequest){
        LocalDate bookingDate = preBookingRequest.bookingDate();
        LocalTime bookingTime = preBookingRequest.bookingTime();
        LocalTime now = LocalTime.now().withNano(0);
        LocalTime minTime = LocalTime.of(10, 0);
        LocalTime maxTime = LocalTime.of(19, 30);

        if (bookingDate.isBefore(LocalDate.now())) {
            throw new BaseException(BookingErrorCode.INVALID_BOOKING_DATE);
        }

        if (bookingDate.isEqual(LocalDate.now()) && bookingTime.isBefore(now)) {
            throw new BaseException(BookingErrorCode.INVALID_BOOKING_TIME);
        }

        if (bookingTime.getMinute() % 30 != 0) {
            throw new BaseException(BookingErrorCode.INVALID_TIME_FORMAT);
        }

        if (bookingTime.isBefore(minTime) || bookingTime.isAfter(maxTime)) {
            throw new BaseException(BookingErrorCode.OUT_OF_AVAILABLE_TIME_RANGE);
        }

    }
    @Transactional(readOnly = true)
    public List<LocalDate> getAvailableDates(Long designerId){
        LocalDate today = LocalDate.now();
        LocalDate endDate = YearMonth.from(today.plusMonths(3)).atEndOfMonth();

        List<LocalDate> allDates = today.datesUntil(endDate.plusDays(1))
                .toList();

        List<LocalDate> fullyBookedDates = designerScheduleAdaptor.findFullyBookedDates(designerId, today, endDate, MAX_SLOTS);

        return allDates.stream()
                .filter(date -> {
                    if (date.equals(today) && LocalTime.now().isAfter(LocalTime.of(19, 30))) {
                        return false;
                    }
                    if (date.equals(today)) {
                        return hasAvailableTimesToday(designerId, today);
                    }
                    return !fullyBookedDates.contains(date);
                })
                .collect(Collectors.toList());
    }


    // 오늘 날짜에서 현재 시간 이후 가능한 시간 있는지 확인
    private boolean hasAvailableTimesToday(Long designerId, LocalDate today) {
        List<LocalTime> bookedTimes = designerScheduleAdaptor.findBookedTimes(designerId, today);

        LocalTime now = LocalTime.now().withSecond(0).withNano(0);
        return TIME_SLOTS.stream()
                .anyMatch(time -> time.isAfter(now) && !bookedTimes.contains(time));
    }

    // 해당 날짜에서 예약 가능 시간 반환
    public List<LocalTime> getAvailableTimes(Long designerId, LocalDate bookingDate) {

        LocalDate today = LocalDate.now();
        LocalDate endDate = YearMonth.from(today.plusMonths(3)).atEndOfMonth();

        if (bookingDate == null || bookingDate.isBefore(today) || bookingDate.isAfter(endDate)) {
            throw new BaseException(BookingErrorCode.INVALID_BOOKING_DATE);
        }

        List<LocalTime> bookedTimes = designerScheduleAdaptor.findBookedTimes(designerId, bookingDate);
        List<LocalTime> availableTimes = AvailableTimeSlots.TIME_SLOTS;

        if (bookingDate.equals(LocalDate.now())) {
            LocalTime now = LocalTime.now().withSecond(0).withNano(0);
            availableTimes = availableTimes.stream()
                    .filter(time -> time.isAfter(now))
                    .toList();
        }

        return availableTimes.stream()
                .filter(time -> !bookedTimes.contains(time))
                .collect(Collectors.toList());
    }

}
