package com.cube.order.mappers;

import com.cube.order.dtos.internal.asaas.request.GeneratePaymentBodyDTO;
import com.cube.order.dtos.internal.asaas.request.GeneratePaymentDTO;
import com.cube.order.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    GeneratePaymentBodyDTO bodyToBuilt(GeneratePaymentDTO generatePaymentDTO);

    GeneratePaymentBodyDTO orderBodyToBuiltPayment(Order order);
}
