package com.cube.order.dtos.internal.asaas.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDTO {

    private String creditCardNumber;

    private String creditCardBrand;

    private String creditCardToken;

}
