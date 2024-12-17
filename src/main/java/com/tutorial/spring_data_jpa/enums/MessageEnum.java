package com.tutorial.spring_data_jpa.enums;

public enum MessageEnum {
    NOT_FOUND(1, "The record is not found!"),
    NOT_AUTHORIZED(2, "You are not authorized for this process!"),
    UNEXPECTED_ERROR(3, "Unexpected error!"),
    SAVE_SUCCESS(4, "The record has been saved successfully!"),
    FETCH_SUCCESS(5, "The record has been fetched successfully!"),
    UPDATE_SUCCESS(6, "The record has been updated successfully!"),
    DELETE_SUCCESS(7, "The record has been deleted successfully!"),
    RECORD_EXISTS(8, "The record already exists!"),
    EMPTY_LIST(9, "The list is empty!");

    private final Integer code;
    private final String message;
    
    MessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
