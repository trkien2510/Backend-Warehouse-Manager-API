package com.trkien.WarehouseManager.dto.response;

import java.time.LocalDateTime;

public class AuditLogResponse {
    private String action;
    private LocalDateTime timestamp;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
