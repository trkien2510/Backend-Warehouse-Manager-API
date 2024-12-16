package com.trkien.WarehouseManager.services;

import com.trkien.WarehouseManager.dto.request.AuditLogCreateRequest;
import com.trkien.WarehouseManager.dto.response.AuditLogResponse;
import com.trkien.WarehouseManager.entity.AuditLog;
import com.trkien.WarehouseManager.entity.User;
import com.trkien.WarehouseManager.exception.AppException;
import com.trkien.WarehouseManager.exception.ErrorCode;
import com.trkien.WarehouseManager.mapper.AuditLogMapper;
import com.trkien.WarehouseManager.repository.AuditLogRepository;
import com.trkien.WarehouseManager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {
    private final AuditLogMapper auditLogMapper;
    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    public AuditLogService(AuditLogMapper auditLogMapper, AuditLogRepository auditLogRepository, UserRepository userRepository) {
        this.auditLogMapper = auditLogMapper;
        this.auditLogRepository = auditLogRepository;
        this.userRepository = userRepository;
    }

    public List<AuditLogResponse> getAllLogByUser(String loggedInUsername){
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        return auditLogRepository.findByUserId(user.getId()).stream().map(auditLogMapper::toAuditLogResponse).toList();
    }

    public AuditLogResponse createAuditLog(AuditLogCreateRequest request, String loggedInUsername){
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        AuditLog auditLog = auditLogMapper.toAuditLog(request, user);
        auditLogRepository.save(auditLog);
        return auditLogMapper.toAuditLogResponse(auditLog);
    }
}
