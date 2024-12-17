package com.tutorial.spring_data_jpa.payload;

import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;

/**
 * Generic API response payload.
 *
 * @param <T> The type of the data payload.
 */
public class ResponsePayload<T> {
    private final Integer statusCode;
    private final String description;
    private final Boolean isSuccess;
    private final String message;
    private final T data;
    private final Boolean showNotification;

    private ResponsePayload(Builder<T> builder) {
        this.statusCode = builder.statusCode;
        this.description = builder.description;
        this.isSuccess = builder.isSuccess;
        this.message = builder.message;
        this.data = builder.data;
        this.showNotification = builder.showNotification;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Boolean getShowNotification() {
        return showNotification;
    }

    /**
     * Builder class for ResponsePayload.
     *
     * @param <T> The type of the data payload.
     */
    public static class Builder<T> {
        private Integer statusCode;
        private String description;
        private Boolean isSuccess;
        private String message;
        private T data;
        private Boolean showNotification;

        public Builder() {
            // default constructor
        }

        public Builder<T> statusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder<T> description(String description) {
            this.description = description;
            return this;
        }

        public Builder<T> isSuccess(Boolean isSuccess) {
            this.isSuccess = isSuccess;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> showNotification(Boolean showNotification) {
            this.showNotification = showNotification;
            return this;
        }

        public ResponsePayload<T> build() {
            return new ResponsePayload<>(this);
        }
    }

    public static <T> ResponsePayload<T> success() {
        return new Builder<T>()
                .statusCode(ResponseEnum.OK.getHttpStatusCode())
                .description(ResponseEnum.OK.getDescription())
                .isSuccess(ResponseEnum.OK.getSuccess())
                .showNotification(false)
                .build();
    }

    public static <T> ResponsePayload<T> success(T data) {
        return new Builder<T>()
                .statusCode(ResponseEnum.OK.getHttpStatusCode())
                .description(ResponseEnum.OK.getDescription())
                .isSuccess(ResponseEnum.OK.getSuccess())
                .data(data)
                .showNotification(false)
                .build();
    }

    public static <T> ResponsePayload<T> success(String message, T data) {
        return new Builder<T>()
                .statusCode(ResponseEnum.OK.getHttpStatusCode())
                .description(ResponseEnum.OK.getDescription())
                .isSuccess(ResponseEnum.OK.getSuccess())
                .message(message)
                .data(data)
                .showNotification(false)
                .build();
    }

    public static <T> ResponsePayload<T> error(ResponseEnum responseEnum, String message, Boolean showNotification) {
        return new Builder<T>()
                .statusCode(responseEnum.getHttpStatusCode())
                .description(responseEnum.getDescription())
                .isSuccess(responseEnum.getSuccess())
                .message(message)
                .showNotification(showNotification)
                .build();
    }

    public static <T> ResponsePayload<T> error(ResponseEnum responseEnum, MessageEnum messageEnum, Boolean showNotification) {
        return new Builder<T>()
                .statusCode(responseEnum.getHttpStatusCode())
                .description(responseEnum.getDescription())
                .isSuccess(responseEnum.getSuccess())
                .message(messageEnum.getMessage())
                .showNotification(showNotification)
                .build();
    }
}
