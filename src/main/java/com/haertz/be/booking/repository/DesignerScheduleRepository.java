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
            "AND ds.designerScheduleId = :designerScheduleId " +
            "AND ds.bookingDate = :bookingDate " +
            "AND ds.bookingTime = :bookingTime")
    boolean hasUserBooked(@Param("userId") Long userId,
                                @Param("designerScheduleId") Long designerScheduleId,
                                @Param("bookingDate") LocalDate bookingDate,
                                @Param("bookingTime") LocalTime bookingTime);
}