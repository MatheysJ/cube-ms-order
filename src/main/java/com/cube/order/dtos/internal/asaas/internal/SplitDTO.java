package com.cube.order.dtos.internal.asaas.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SplitDTO {

    private String walletId;

    private BigDecimal fixedValue;

    private BigDecimal percentualValue;

    private BigDecimal totalFixedValue;

    private String externalReference;

    private String description;

}
