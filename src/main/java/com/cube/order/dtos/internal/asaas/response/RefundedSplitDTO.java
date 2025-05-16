package com.cube.order.dtos.internal.asaas.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundedSplitDTO {

    private String id;

    private BigDecimal value;

    private Boolean done;

}
