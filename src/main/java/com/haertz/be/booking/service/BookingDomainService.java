package com.haertz.be.booking.service;

import com.haertz.be.booking.adaptor.BookingAdaptor;
import com.haertz.be.booking.converter.BookingConverter;
import com.haertz.be.booking.dto.request.BookingInfoRequest;
import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.entity.BookingStatus;
import com.haertz.be.booking.entity.DesignerSchedule;
import com.haertz.be.booking.entity.MeetingType;
import com.haertz.be.booking.exception.BookingErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.entity.MeetingMode;
import com.haertz.be.designer.exception.DesignerErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingDomainService {
    private final BookingAdaptor bookingAdaptor;
    @Transactional
    public Booking book(BookingInfoRequest bookingInfo, Designer designer, Long userId, DesignerSchedule schedule){
        validateMeetingType(bookingInfo.meetingType(), designer.getMeetingMode());
        Booking book = BookingConverter.toBooking(bookingInfo, designer, userId, schedule);
        return bookingAdaptor.save(book);
    }

    private void validateMeetingType(MeetingType requestMeetingType, MeetingMode designerMeetingMode) {
        if (designerMeetingMode == MeetingMode.BOTH) {
            return;
        }

        MeetingType meetingType = designerMeetingMode.toMeetingType()
                .orElseThrow(() -> new BaseException(DesignerErrorCode.INVALID_MEETING_MODE));

        if (meetingType != requestMeetingType) {
            throw new BaseException(DesignerErrorCode.DESIGNER_UNSUPPORTED_MEETING);
        }
    }


    @Transactional
    public void cancelBooking(Booking booking){
        if (!(booking.getBookingStatus().equals(BookingStatus.CANCELED))){
            booking.refundAndCancelBooking();
            bookingAdaptor.save(booking);
        } else throw new BaseException(BookingErrorCode.BOOKING_ALREADY_CANCELED);
    }

}
