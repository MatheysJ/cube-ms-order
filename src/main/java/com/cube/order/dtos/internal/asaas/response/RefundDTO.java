package com.cube.order.dtos.internal.asaas.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundDTO {

    private LocalDateTime dateCreated;

    private String status;

    private BigDecimal value;

    private String endToEndIdentifier;

    private String description;

    private LocalDateTime effectiveDate;

    private String transactionReceiptUrl;

    private List<RefundedSplitDTO> refundedSplits;

}
