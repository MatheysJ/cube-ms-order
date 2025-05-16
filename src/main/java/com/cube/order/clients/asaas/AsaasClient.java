package com.cube.order.clients.asaas;

import com.cube.order.dtos.internal.asaas.request.GeneratePaymentBodyDTO;
import com.cube.order.dtos.internal.asaas.response.GeneratedPaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("asaas")
public interface AsaasClient {

    @PostMapping(value = "/v3/payments")
    GeneratedPaymentDTO generatePayment(@RequestBody GeneratePaymentBodyDTO generatePaymentDTO);

}
