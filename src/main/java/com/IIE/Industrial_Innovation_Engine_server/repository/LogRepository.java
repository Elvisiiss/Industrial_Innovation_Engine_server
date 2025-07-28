package com.IIE.Industrial_Innovation_Engine_server.repository;

import com.IIE.Industrial_Innovation_Engine_server.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<Log> findByTimestampBetweenAndUserId(LocalDateTime start, LocalDateTime end, Long userId);
    List<Log> findByTimestampBetweenAndAction(LocalDateTime start, LocalDateTime end, String action);
    List<Log> findByTimestampBetweenAndUserIdAndAction(LocalDateTime start, LocalDateTime end, Long userId, String action);
}
