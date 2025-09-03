package com.cube.order.services;

import com.cube.order.clients.asaas.AsaasClient;
import com.cube.order.clients.product.ProductClient;
import com.cube.order.dtos.internal.asaas.request.CallbackDTO;
import com.cube.order.dtos.internal.asaas.request.GeneratePaymentBodyDTO;
import com.cube.order.dtos.internal.asaas.request.GeneratePaymentDTO;
import com.cube.order.dtos.internal.asaas.response.GeneratedPaymentDTO;
import com.cube.order.dtos.request.AsaasWebHookBody;
import com.cube.order.dtos.response.CatalogItemDTO;
import com.cube.order.mappers.ItemMapper;
import com.cube.order.mappers.PaymentMapper;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private OrderMapper orderMapper;

    private PaymentMapper paymentMapper;

    private ItemMapper itemMapper;

    private ProductClient productClient;

    private AsaasClient asaasClient;

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
    public ResponseOrderDTO submitOrder(SubmitOrderDTO body, String user, String asaasCustomerId) {
        log.info("Started submitting user's {} new order", user);
        RequestOrderDTO requestOrderDTO = orderMapper.submitToRequest(body);
        requestOrderDTO.setUserId(user);
        requestOrderDTO.setItems(populateOrderItems(body.getItems()));

        Order newOrder = orderMapper.requestToModel(requestOrderDTO);

        if (newOrder.getItems() != null) {
            newOrder.getItems().forEach(item -> item.setOrder(newOrder));
        }

        GeneratePaymentBodyDTO builtPaymentBody = buildPayment(newOrder, asaasCustomerId);

        log.info("Started to generate order on Asaas");
        GeneratedPaymentDTO generatedPaymentDTO = asaasClient.generatePayment(builtPaymentBody);

        newOrder.setPrice(generatedPaymentDTO.getValue().doubleValue());
        newOrder.setInvoiceUrl(generatedPaymentDTO.getInvoiceUrl());
        newOrder.setStatus("PENDING");
        newOrder.setAsaasOrderId(generatedPaymentDTO.getId());

        Order savedOrder = orderRepository.save(newOrder);

        log.info("Started returning user's {} new order", user);
        ResponseOrderDTO responseOrderDTO = orderMapper.modelToResponse(savedOrder);
        return responseOrderDTO;
    }

    @Override
    public GeneratedPaymentDTO submitAsaas(String customerId, String asaasCustomerId, GeneratePaymentDTO body) {
        log.info("Started submitAsaas");

        GeneratePaymentBodyDTO builtPaymentBody = buildAsaasPayment(body, asaasCustomerId);
        GeneratedPaymentDTO generatedPaymentDTO = asaasClient.generatePayment(builtPaymentBody);

        log.info("Started submitAsaas");
        return generatedPaymentDTO;
    }

    @Override
    public void changeOrderStatus(AsaasWebHookBody body) {
        log.info("Started to update order status in webHook");

        log.info("{}", body);
        Optional<Order> order = orderRepository.findByAsaasOrderId(body.getPayment().getId());

        if (order.isEmpty()) {
            throw new NotFoundException(ExceptionCode.ASAAS_ORDER_WITH_ID_DOESNT_EXIST);
        }

        Order pendingOrder = order.get();

        pendingOrder.setStatus("PAID");
        orderRepository.save(pendingOrder);

        log.info("Successfully updated order status in webHook");
    }

    private GeneratePaymentBodyDTO buildPayment(Order order, String asaasCustomerId) {
        GeneratePaymentBodyDTO generatePaymentBodyDTO = paymentMapper.orderBodyToBuiltPayment(order);

        generatePaymentBodyDTO.setCustomer(asaasCustomerId);
        generatePaymentBodyDTO.setDueDate(LocalDate.now());
        generatePaymentBodyDTO.setCallback(
                CallbackDTO
                        .builder()
                        .autoRedirect(true)
                        .successUrl("https://cube.matheus-junior.xyz/orders/" + order.getId())
                        .build()
        );
        generatePaymentBodyDTO.setValue(BigDecimal.valueOf(order.getPrice()));

        return generatePaymentBodyDTO;
    }

    private GeneratePaymentBodyDTO buildAsaasPayment(GeneratePaymentDTO body, String asaasCustomerId) {
        GeneratePaymentBodyDTO generatePaymentBodyDTO = paymentMapper.bodyToBuilt(body);

        generatePaymentBodyDTO.setCustomer(asaasCustomerId);
        generatePaymentBodyDTO.setDueDate(LocalDate.now());

        return generatePaymentBodyDTO;
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

    private List<Item> populateOrderItems(List<RequestItemDTO> requestItems) {
        try {
            log.info("Started getting product data by id from client");
            List<CatalogItemDTO> catalogItems = productClient.getProductsByIds(requestItems);

            List<Item> items = itemMapper.catalogToModel(catalogItems);
            log.info("Successfully got product data by id from client");

            if (requestItems.size() != catalogItems.size()) throw new NotFoundException(ExceptionCode.PRODUCT_NOT_FOUND);

            Map<String, Integer> requestItemQuantities = requestItems.stream()
                    .collect(Collectors.toMap(
                            RequestItemDTO::getId,
                            RequestItemDTO::getQuantity
                    ));

            items.forEach(item -> {
                Integer quantity = requestItemQuantities.get(item.getId());
                if (quantity != null) {
                    item.setQuantity(quantity);
                }
            });

            return items;
        } catch (Exception exception) {
            log.error("Error populating order items", exception);

            throw exception;
        }
    }
}