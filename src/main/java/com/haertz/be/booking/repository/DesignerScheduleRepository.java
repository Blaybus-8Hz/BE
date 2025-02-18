package com.haertz.be.booking.repository;

import com.haertz.be.booking.entity.DesignerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface DesignerScheduleRepository extends JpaRepository<DesignerSchedule, Long> {
    @Query("SELECT COUNT(ds) > 0 FROM DesignerSchedule ds " +
            "WHERE ds.userId = :userId " +
            "AND ds.designerScheduleId = :designerScheduleId " )
    boolean hasUserBooked(@Param("userId") Long userId,
                                @Param("designerScheduleId") Long designerScheduleId);

    @Query("SELECT COUNT(ds) > 0 FROM DesignerSchedule ds " +
            "WHERE ds.designerId = :designerId " +
            "AND ds.bookingDate = :bookingDate " +
            "AND ds.bookingTime = :bookingTime")
    boolean hasBookingByTimeSlot(@Param("designerId") Long designerId,
                                 @Param("bookingDate") LocalDate bookingDate,
                                 @Param("bookingTime") LocalTime bookingTime);


}