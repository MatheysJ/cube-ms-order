package com.cube.order.dtos.internal.asaas.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {

    private BigDecimal value;

    private Integer dueDateLimitDays;

    private String type;

}
