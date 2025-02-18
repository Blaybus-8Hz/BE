package com.haertz.be.designer.repository;

import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.entity.Specialty;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignerRepository extends JpaRepository<Designer, Long> {

    Designer findByDesignerId(Long designerId);
    List<Designer> findByDesignerSpecialty(Specialty specialty);

    @Override
    @NonNull
    List<Designer> findAll();
}
