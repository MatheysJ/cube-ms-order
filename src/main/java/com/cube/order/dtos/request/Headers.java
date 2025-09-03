package com.cube.order.dtos.request;

import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Headers {
    @Valid
    @NotNull
    String customer_id;

    @Valid
    @NotNull
    String asaas_customer_id;
}
