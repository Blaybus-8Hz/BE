package com.haertz.be.booking.repository;

import com.haertz.be.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByDesignerIdAndBookingDateAndBookingTime(Long designerId, LocalDate bookingDate, LocalTime bookingTime);
}
