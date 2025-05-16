package com.cube.order.dtos.internal.asaas.response;

import com.cube.order.dtos.internal.asaas.internal.FineDTO;
import com.cube.order.dtos.internal.asaas.internal.InterestDTO;
import com.cube.order.dtos.internal.asaas.internal.SplitDTO;
import com.cube.order.dtos.internal.asaas.request.DiscountDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedPaymentDTO {

    private String object;

    private String id;

    private LocalDate dateCreated;

    private String customer;

    private String subscription;

    private String installment;

    private String paymentLink;

    private BigDecimal value;

    private BigDecimal netValue;

    private BigDecimal originalValue;

    private BigDecimal interestValue;

    private String description;

    private String billingType;

    private CreditCardDTO creditCard;

    private Boolean canBePaidAfterDueDate;

    private String pixTransaction;

    private String pixQrCodeId;

    private String status;

    private LocalDate dueDate;

    private LocalDate originalDueDate;

    private LocalDate paymentDate;

    private LocalDate clientPaymentDate;

    private Integer installmentNumber;

    private String invoiceUrl;

    private String invoiceNumber;

    private String externalReference;

    private Boolean deleted;

    private Boolean anticipated;

    private Boolean anticipable;

    private LocalDate creditDate;

    private LocalDate estimatedCreditDate;

    private String transactionReceiptUrl;

    private String nossoNumero;

    private String bankSlipUrl;

    private DiscountDTO discount;

    private FineDTO fine;

    private InterestDTO interest;

    private List<SplitDTO> split;

    private Boolean postalService;

    private Integer daysAfterDueDateToRegistrationCancellation;

    private ChargebackDTO chargeback;

    private EscrowDTO escrow;

    private List<RefundDTO> refunds;

}
