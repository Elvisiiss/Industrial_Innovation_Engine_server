package com.IIE.Industrial_Innovation_Engine_server.service;

import com.IIE.Industrial_Innovation_Engine_server.entity.Log;
import java.time.LocalDateTime;
import java.util.List;

public interface LogService {
    List<Log> getLogs(LocalDateTime startDate, LocalDateTime endDate, Long userId, String actionType);
}
