package com.cube.order.dtos.internal.asaas.request;

import com.cube.order.dtos.internal.asaas.internal.FineDTO;
import com.cube.order.dtos.internal.asaas.internal.InterestDTO;
import com.cube.order.dtos.internal.asaas.internal.SplitDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratePaymentBodyDTO {

    private String customer;

    private LocalDate dueDate;

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
