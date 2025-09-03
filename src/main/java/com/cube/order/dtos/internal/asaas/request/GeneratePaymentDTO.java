package com.cube.order.dtos.internal.asaas.request;

import com.cube.order.dtos.internal.asaas.internal.FineDTO;
import com.cube.order.dtos.internal.asaas.internal.InterestDTO;
import com.cube.order.dtos.internal.asaas.internal.SplitDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratePaymentDTO {

    private String billingType;

    private BigDecimal value;

    private String description;

    private String externalReference;

    private Integer installmentCount;

    private BigDecimal totalValue;

    private BigDecimal installmentValue;

    private DiscountDTO discount;

    private InterestDTO interest;

    private FineDTO fine;

    private Boolean postalService;

    private List<SplitDTO> split;

    private CallbackDTO callback;

}
