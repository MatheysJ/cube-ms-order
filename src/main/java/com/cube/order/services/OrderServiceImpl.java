package com.cube.order.services;

import com.cube.order.clients.ProductClient;
import com.cube.order.models.Item;
import com.cube.order.dtos.request.RequestItemDTO;
import com.cube.order.dtos.request.RequestOrderDTO;
import com.cube.order.dtos.request.SubmitOrderDTO;
import com.cube.order.dtos.response.ResponseOrderDTO;
import com.cube.order.enums.ExceptionCode;
import com.cube.order.exceptions.NotFoundException;
import com.cube.order.mappers.OrderMapper;
import com.cube.order.models.Order;
import com.cube.order.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private OrderMapper orderMapper;

    private ProductClient productClient;

    @Override
    public List<ResponseOrderDTO> getAllOrders() {
        log.info("Started getting all orders");

        Stream<Order> orders = orderRepository.findAll().stream();

        log.info("Started returning all orders");
        return orders
                .map(orderMapper::modelToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseOrderDTO getOrderById(Long id) {
        log.info("Started getting the order with id {}", id);

        Order order = getByIdOrThrowNotFoundError(id);

        log.info("Started returning the order with id {}", id);
        return orderMapper.modelToResponse(order);
    }

    @Override
    public List<ResponseOrderDTO> getOrdersByUser(String user) {
        log.info("Started getting all user {} orders", user);

        List<Order> orders = orderRepository.findAllByUserId(user);

        log.info("Started returning all user {} orders", user);
        return orders.stream().map(orderMapper::modelToResponse).collect(Collectors.toList());
    }

    @Override
    public ResponseOrderDTO getOrderByUserAndId(String user, Long id) {
        log.info("Started getting user's {} order with id {}", user, id);

        Order order = getByUserAndIdOrThrowNotFoundError(user, id);

        log.info("Started returning user's {} order with id {}", user, id);
        return orderMapper.modelToResponse(order);
    }

    @Override
    public ResponseOrderDTO submitOrder(SubmitOrderDTO body, String user) {
        log.info("Started submitting user's {} new order", user);

        RequestOrderDTO requestOrderDTO = orderMapper.submitToRequest(body);
        requestOrderDTO.setUserId(user);
        requestOrderDTO.setItems(populateOrderItems(body.getItems()));
        Order order = orderRepository.save(orderMapper.requestToModel(requestOrderDTO));

        log.info("Started returning user's {} new order", user);
        return orderMapper.modelToResponse(order);
    }

    private Order getByIdOrThrowNotFoundError(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isEmpty()) {
            throw new NotFoundException(ExceptionCode.ORDER_WITH_ID_DOESNT_EXIST);
        }

        return optionalOrder.get();
    }

    private Order getByUserAndIdOrThrowNotFoundError(String user, Long id) {
        Optional<Order> optionalOrder = orderRepository.findByUserIdAndId(user, id);

        if (optionalOrder.isEmpty()) {
            throw new NotFoundException(ExceptionCode.USER_ORDER_WITH_ID_DOESNT_EXIST);
        }

        return optionalOrder.get();
    }

    private List<Item> populateOrderItems(List<RequestItemDTO> ids) {
        log.info("Started getting product data by id from client");

        return List.of();
        /*
        List<Item> items = productClient.getProductsByIds(ids);

        log.info("Successfully got product data by id from client");
        return items;*/
    }
}
