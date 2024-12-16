package com.trkien.WarehouseManager.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_KEY(999, "Error"),
    INVALID_LOGIN(101, "Login failed"),
    CANNOT_DELETE(102, "Delete failed"),
    CANNOT_UPDATE(103, "Update failed"),
    USER_EXISTED(104, "Username đã tồn tại"),
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
