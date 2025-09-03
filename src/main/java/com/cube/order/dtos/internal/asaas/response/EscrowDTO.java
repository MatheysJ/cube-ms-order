package com.cube.order.dtos.internal.asaas.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EscrowDTO {

    private String id;

    private String status;

    private LocalDate expirationDate;

    private LocalDate finishDate;

    private String finishReason;

}
