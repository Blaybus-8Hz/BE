package com.haertz.be.designer.repository;

import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.entity.District;
import com.haertz.be.designer.entity.MeetingMode;
import com.haertz.be.designer.entity.Specialty;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignerRepository extends JpaRepository<Designer, Long> {

    Page<Designer> findAll(@NonNull Pageable pageable);

    @Query("SELECT DISTINCT d FROM Designer d WHERE " +
            "(:meetingMode IS NULL OR d.meetingMode = :meetingMode OR d.meetingMode = 'BOTH') AND " +
            "(:district IS NULL OR d.designerDistrict = :district) AND " +
            "(:specialties IS NULL OR d.designerSpecialty IN :specialties)")
    Page<Designer> findFilteredDesigners(
            @Nullable @Param("meetingMode") MeetingMode meetingMode,
            @Nullable @Param("district") District district,
            @Nullable @Param("specialties") List<Specialty> specialties,
            Pageable pageable
    );


    Designer findByDesignerId(Long designerId);
    List<Designer> findByDesignerSpecialty(Specialty specialty);
    List<Designer> findByDesignerDistrict(District district);
    List<Designer> findByMeetingMode(MeetingMode meetingMode);
 }
