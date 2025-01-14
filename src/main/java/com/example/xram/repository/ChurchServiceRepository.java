package com.example.xram.repository;

import com.example.xram.model.ChurchService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface ChurchServiceRepository extends JpaRepository<ChurchService, Long> {
    List<ChurchService> findByStartTimeBetweenAndIsActiveOrderByStartTime(
            LocalDateTime start, LocalDateTime end, boolean isActive);

    @Query("SELECT cs FROM ChurchService cs WHERE cs.startTime >= CURRENT_DATE AND cs.isActive = true ORDER BY cs.startTime")
    List<ChurchService> findUpcomingServices();
}