package com.trkien.WarehouseManager.dto.request;

public class AuditLogCreateRequest {
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
