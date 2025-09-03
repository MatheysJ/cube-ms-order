package com.cube.order.exceptions;

import com.cube.order.enums.ExceptionCode;

public class InternalException extends BusinessException {

    public InternalException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

}
