package com.cube.order.exceptions;

import com.cube.order.enums.ExceptionCode;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
