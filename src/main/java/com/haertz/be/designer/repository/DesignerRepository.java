package com.haertz.be.designer.repository;

import com.haertz.be.designer.entity.Designer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerRepository extends JpaRepository<Designer, Long> {

    Designer findByDesignerId(Long designerId);
}
