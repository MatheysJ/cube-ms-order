package com.cube.order.dtos.response;

import com.cube.order.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrderDTO {

    private String id;

    private String asaasOrderId;

    private Double price;

    private List<ResponseItemDTO> items;

    private PaymentMethod billingType;

    private String invoiceUrl;

    private String status;

}
