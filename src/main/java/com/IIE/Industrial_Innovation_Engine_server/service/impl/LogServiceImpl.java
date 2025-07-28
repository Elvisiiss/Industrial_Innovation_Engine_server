package com.IIE.Industrial_Innovation_Engine_server.service.impl;

import com.IIE.Industrial_Innovation_Engine_server.entity.Log;
import com.IIE.Industrial_Innovation_Engine_server.repository.LogRepository;
import com.IIE.Industrial_Innovation_Engine_server.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<Log> getLogs(LocalDateTime startDate, LocalDateTime endDate, Long userId, String actionType) {
        // 处理空值情况
        if (startDate == null) startDate = LocalDateTime.MIN;
        if (endDate == null) endDate = LocalDateTime.MAX;

        if (userId != null && actionType != null) {
            return logRepository.findByTimestampBetweenAndUserIdAndAction(startDate, endDate, userId, actionType);
        } else if (userId != null) {
            return logRepository.findByTimestampBetweenAndUserId(startDate, endDate, userId);
        } else if (actionType != null) {
            return logRepository.findByTimestampBetweenAndAction(startDate, endDate, actionType);
        } else {
            return logRepository.findByTimestampBetween(startDate, endDate);
        }
    }
}
