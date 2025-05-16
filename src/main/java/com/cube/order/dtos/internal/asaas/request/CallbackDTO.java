package com.cube.order.dtos.internal.asaas.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallbackDTO {

    private String successUrl;

    private Boolean autoRedirect;

}
