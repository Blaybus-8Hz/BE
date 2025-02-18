package com.haertz.be.googlemeet.repository;

import com.haertz.be.designer.entity.DesignerMeetingLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface DesignerMeetingLinkRepository extends JpaRepository<DesignerMeetingLink, Long> {
    // 디자이너 ID와 시작 시간을 기준으로 미팅 링크를 조회하는 메소드
    Optional<DesignerMeetingLink> findByDesignerDesignerIdAndStartTime(Long designerId, LocalTime startTime);
}