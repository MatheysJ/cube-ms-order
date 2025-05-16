package com.cube.order.dtos.internal.asaas.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargebackDTO {

    private String id;

    private String payment;

    private String installment;

    private String customerAccount;

    private String status;

    private String reason;

    private LocalDate disputeStartDate;

    private BigDecimal value;

    private LocalDate paymentDate;

    private ChargebackCreditCardDTO creditCard;

    private String disputeStatus;

    private LocalDate deadlineToSendDisputeDocuments;

}
