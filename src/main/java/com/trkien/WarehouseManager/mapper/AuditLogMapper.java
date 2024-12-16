package com.trkien.WarehouseManager.mapper;

import com.trkien.WarehouseManager.dto.request.AuditLogCreateRequest;
import com.trkien.WarehouseManager.dto.response.AuditLogResponse;
import com.trkien.WarehouseManager.dto.response.UserResponse;
import com.trkien.WarehouseManager.entity.AuditLog;
import com.trkien.WarehouseManager.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditLogMapper {
    public AuditLog toAuditLog(AuditLogCreateRequest request, User user){
        if (request == null){
            return null;
        } else {
            return new AuditLog(request.getAction(), LocalDateTime.now(), user);
        }
    }

    public AuditLogResponse toAuditLogResponse(AuditLog log){
        AuditLogResponse response = new AuditLogResponse();
        response.setAction(log.getAction());
        response.setTimestamp(log.getTimestamp());
        return response;
    }
}
