package com.example.peking.enums;

public enum ResponseInfoEnum {
    REQUEST_SUCCESSFULLY(200, "ok", "Request successfully"),
    SERVER_INTERNAL_ERROR(500, "error", "Server internal error"),
    RESOURCE_NOT_FOUND(404, "not found", "Requested resources not found"),
    REQUEST_NOT_ACCEPTABLE(406, "Not Acceptable", "Request data is not match the requirement"),
    RESOURCE_CREATED(201, "Created", "Requested resource created"),
    RESOURCE_UPDATED(203, "Updated", "Resource updated"),
    NO_CONTENT(204, "NO Content", "Delete content successfully");

    private Integer code;
    private String message;
    private String description;

    ResponseInfoEnum(Integer code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}