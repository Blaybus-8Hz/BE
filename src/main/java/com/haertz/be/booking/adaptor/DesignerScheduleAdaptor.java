package com.haertz.be.booking.adaptor;

import com.haertz.be.booking.dto.request.BookingInfoRequest;
import com.haertz.be.booking.entity.DesignerSchedule;
import com.haertz.be.booking.exception.BookingErrorCode;
import com.haertz.be.booking.repository.DesignerScheduleRepository;
import com.haertz.be.common.annotation.Adaptor;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.payment.entity.PaymentStatus;
import com.haertz.be.payment.exception.PaymentErrorCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Adaptor
@RequiredArgsConstructor
public class DesignerScheduleAdaptor {
    private final DesignerScheduleRepository designerScheduleRepository;

    public DesignerSchedule save(DesignerSchedule designerSchedule) {
        return designerScheduleRepository.save(designerSchedule);
    }
    public Boolean hasUserBookedSchedule(Long userId, Long designerScheduleId) {
        return designerScheduleRepository.hasUserBooked(userId, designerScheduleId);
    }

    public Boolean hasBookingByTimeSlot(Long designerId, LocalDate bookingDate, LocalTime bookingTime) {
        return designerScheduleRepository.hasBookingByTimeSlot(designerId, bookingDate, bookingTime);
    }
    public DesignerSchedule findById(Long designerScheduleId) {
        return designerScheduleRepository.findById(designerScheduleId)
                .orElseThrow(() -> new BaseException(BookingErrorCode.DESIGNER_SCHEDULE_NOT_FOUND));
    }
    public DesignerSchedule isPaymentStatusValid(Long designerScheduleId) {
        DesignerSchedule designerSchedule = findById(designerScheduleId);

        if (designerSchedule.getPaymentStatus() != PaymentStatus.PENDING && designerSchedule.getPaymentStatus() != PaymentStatus.COMPLETED) {
            throw new BaseException(PaymentErrorCode.PAYMENT_STATUS_INVALID);
        }

        return designerSchedule;
    }
    public void deleteById(Long designerScheduleId) {
        designerScheduleRepository.deleteById(designerScheduleId);
    }
}
