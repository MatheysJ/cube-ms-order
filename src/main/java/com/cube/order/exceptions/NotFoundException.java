package com.cube.order.exceptions;

import com.cube.order.enums.ExceptionCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
