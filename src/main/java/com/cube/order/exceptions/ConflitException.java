package com.cube.order.exceptions;

import com.cube.order.enums.ExceptionCode;

public class ConflitException extends BusinessException {

    public ConflitException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
