package com.tutorial.spring_data_jpa.enums;


public enum ResponseEnum {
  OK(200, "Success", Boolean.TRUE),
  BAD_REQUEST(400, "Bad Request", Boolean.FALSE),
  UNAUTHORIZED(401, "Unauthorized", Boolean.FALSE),
  FORBIDDEN(403, "Forbidden", Boolean.FALSE),
  NOT_FOUND(404, "Not Found", Boolean.FALSE),
  INTERNAL_SERVER_ERROR(500, "Internal Server Error", Boolean.FALSE);

  private final Integer httpStatusCode;
  private final String description;
  private final Boolean isSuccess;

  ResponseEnum(Integer httpStatusCode, String description, Boolean isSuccess) {
    this.httpStatusCode = httpStatusCode;
    this.description = description;
    this.isSuccess = isSuccess;
  }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }
}
