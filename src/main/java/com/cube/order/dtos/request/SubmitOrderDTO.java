package com.cube.order.dtos.request;

import com.cube.order.enums.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitOrderDTO {

    @Valid
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentMethod billingType;

    @Valid
    private AddressDTO address;

    @Valid
    @NotNull
    @NotEmpty
    private List<RequestItemDTO> items;

}
