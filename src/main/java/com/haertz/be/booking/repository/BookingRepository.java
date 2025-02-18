package com.haertz.be.booking.repository;

import com.haertz.be.auth.entity.User;
import com.haertz.be.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByDesignerDesignerIdAndBookingDateAndBookingTime(Long designerId, LocalDate bookingDate, LocalTime bookingTime);

    List<Booking> findByUserId(Long userId);

}
