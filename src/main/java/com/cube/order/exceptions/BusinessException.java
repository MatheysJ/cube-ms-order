package com.cube.order.exceptions;

import com.cube.order.enums.ExceptionCode;

public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException (ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());

        this.code = exceptionCode.name();
    }

    public String getCode() {
        return code;
    }
}
