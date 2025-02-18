package com.haertz.be.booking.usecase;

import com.haertz.be.booking.adaptor.BookingAdaptor;
import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.service.BookingDomainService;
import com.haertz.be.booking.service.DesignerScheduleDomainService;
import com.haertz.be.common.annotation.UseCase;
import com.haertz.be.common.exception.GlobalErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.utils.AuthenticatedUserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CancelBookingWithRefundUseCase {

    private final BookingDomainService bookingDomainService;
    private final DesignerScheduleDomainService designerScheduleDomainService;
    private final BookingAdaptor bookingAdaptor;
    private final AuthenticatedUserUtils userUtils;

    @Transactional
    public void execute(Long bookingId){
        Booking booking = bookingAdaptor.findById(bookingId);

        if(!booking.getUserId().equals(userUtils.getCurrentUserId())){
            throw new BaseException(GlobalErrorCode.UNAUTHORIZED_REQUEST_ERROR);
        }

        designerScheduleDomainService.deleteScheduleAfterFailedPayment(booking.getDesignerScheduleId());
        bookingDomainService.cancelBooking(booking);
    }
}
