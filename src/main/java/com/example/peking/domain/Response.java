package com.example.peking.domain;

import com.example.peking.enums.ResponseInfoEnum;

public class Response<T> {

    private Integer code;
    private String message;
    private String description;

    private T data;

    public Response() {
    }

    public Response(ResponseInfoEnum responseInfoEnum) {
        this.code = responseInfoEnum.getCode();
        this.message = responseInfoEnum.getMessage();
        this.description = responseInfoEnum.getDescription();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
