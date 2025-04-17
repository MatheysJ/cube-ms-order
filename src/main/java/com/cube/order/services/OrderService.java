package com.cube.order.services;

import com.cube.order.dtos.request.SubmitOrderDTO;
import com.cube.order.dtos.response.ResponseOrderDTO;

import java.util.List;

public interface OrderService {

    List<ResponseOrderDTO> getAllOrders();

    ResponseOrderDTO getOrderById(Long id);

    List<ResponseOrderDTO> getOrdersByUser(String user);

    ResponseOrderDTO getOrderByUserAndId(String user, Long id);

    ResponseOrderDTO submitOrder(SubmitOrderDTO body, String user);

}
