package com.tutorial.spring_data_jpa.payload;

import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;

public abstract class AbstractResponsePayload {

    protected <T> ResponsePayload<T> createSuccessResponse() {
        return ResponsePayload.success();
    }

    protected <T> ResponsePayload<T> createSuccessResponse(T data) {
        return ResponsePayload.success(data);
    }

    protected <T> ResponsePayload<T> createSuccessResponse(String message, T data) {
        return ResponsePayload.success(message, data);
    }

    protected <T> ResponsePayload<T> createErrorResponse(ResponseEnum responseEnum, String message) {
        return ResponsePayload.error(responseEnum, message, false);
    }

    protected <T> ResponsePayload<T> createErrorResponse(ResponseEnum responseEnum, MessageEnum messageEnum) {
        return ResponsePayload.error(responseEnum, messageEnum, false);
    }

    protected <T> ResponsePayload<T> createErrorResponse(ResponseEnum responseEnum, MessageEnum messageEnum, Boolean showNotification) {
        return ResponsePayload.error(responseEnum, messageEnum, showNotification);
    }

    protected <T> ResponsePayload<T> createErrorResponse(ResponseEnum responseEnum, String message, Boolean showNotification) {
        return ResponsePayload.error(responseEnum, message, showNotification);
    }
}
