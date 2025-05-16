package com.cube.order.services;

import com.cube.order.dtos.internal.asaas.request.GeneratePaymentDTO;
import com.cube.order.dtos.internal.asaas.response.GeneratedPaymentDTO;
import com.cube.order.dtos.request.Headers;
import com.cube.order.dtos.request.SubmitOrderDTO;
import com.cube.order.dtos.response.ResponseOrderDTO;

import java.util.List;

public interface OrderService {

    List<ResponseOrderDTO> getAllOrders();

    ResponseOrderDTO getOrderById(Long id);

    List<ResponseOrderDTO> getOrdersByUser(String user);

    ResponseOrderDTO getOrderByUserAndId(String user, Long id);

    ResponseOrderDTO submitOrder(SubmitOrderDTO body, String user);

    //TODO: Remove
    GeneratedPaymentDTO submitAsaas(String customerId, String asaasCustomerId, GeneratePaymentDTO body);

}
