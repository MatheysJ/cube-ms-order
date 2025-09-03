package com.cube.order.dtos.internal.asaas.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargebackCreditCardDTO {

    private String number;

    private String brand;

}
