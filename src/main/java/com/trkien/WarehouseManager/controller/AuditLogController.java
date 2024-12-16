package com.trkien.WarehouseManager.controller;

import com.trkien.WarehouseManager.dto.ApiResponse;
import com.trkien.WarehouseManager.dto.request.AuditLogCreateRequest;
import com.trkien.WarehouseManager.dto.response.AuditLogResponse;
import com.trkien.WarehouseManager.dto.response.CategoryResponse;
import com.trkien.WarehouseManager.services.AuditLogService;
import com.trkien.WarehouseManager.utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-log")
public class AuditLogController {
    private final AuditLogService auditLogService;
    private final JwtUtils jwtUtils;

    public AuditLogController(AuditLogService auditLogService, JwtUtils jwtUtils) {
        this.auditLogService = auditLogService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public ApiResponse<List<AuditLogResponse>> getAllAuditLog(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        ApiResponse<List<AuditLogResponse>> response = new ApiResponse<>();
        response.setData(auditLogService.getAllLogByUser(loggedInUsername));
        return response;
    }

    @PostMapping
    public ApiResponse<AuditLogResponse> createAuditLog(@RequestHeader("Authorization") String authorizationHeader, @RequestBody AuditLogCreateRequest request){
        String token = authorizationHeader.replace("Bearer ", "");
        String loggedInUsername = jwtUtils.extractUsername(token);

        ApiResponse<AuditLogResponse> response = new ApiResponse<>();
        response.setData(auditLogService.createAuditLog(request, loggedInUsername));
        return response;
    }
}
