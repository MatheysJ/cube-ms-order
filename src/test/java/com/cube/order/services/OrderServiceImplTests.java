package com.cube.order.services;

import com.cube.order.clients.asaas.AsaasClient;
import com.cube.order.clients.product.ProductClient;
import com.cube.order.dtos.internal.asaas.request.GeneratePaymentBodyDTO;
import com.cube.order.dtos.request.RequestOrderDTO;
import com.cube.order.dtos.request.SubmitOrderDTO;
import com.cube.order.dtos.response.CatalogItemDTO;
import com.cube.order.dtos.response.ResponseOrderDTO;
import com.cube.order.exceptions.NotFoundException;
import com.cube.order.factory.OrderFactory;
import com.cube.order.mappers.ItemMapper;
import com.cube.order.mappers.OrderMapper;
import com.cube.order.mappers.PaymentMapper;
import com.cube.order.models.Item;
import com.cube.order.models.Order;
import com.cube.order.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private ProductClient productClient;

    @Mock
    private AsaasClient asaasClient;

    @InjectMocks
    private OrderServiceImpl orderService;

    private final Order mockOrder = OrderFactory.getMockOfOrder();
    private final ResponseOrderDTO mockResponseOrder = OrderFactory.getMockOfResponseOrder();
    private final SubmitOrderDTO mockSubmitOrder = OrderFactory.getMockOfSubmitOrder();
    private final String mockUserId = OrderFactory.getMockOfUserId();
    private final String mockAsaasCustomerId = OrderFactory.getMockOfAsaasCustomerId();

    @Test
    void shouldGetAllOrdersAndMapThemToResponse() {
        Mockito.when(orderRepository.findAll()).thenReturn(List.of(mockOrder));
        Mockito.when(orderMapper.modelToResponse(Mockito.any(Order.class))).thenReturn(mockResponseOrder);

        List<ResponseOrderDTO> response = orderService.getAllOrders();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(mockResponseOrder, response.getFirst());
    }

    @Test
    void shouldGetOrderById() {
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockOrder));
        Mockito.when(orderMapper.modelToResponse(mockOrder)).thenReturn(mockResponseOrder);

        ResponseOrderDTO response = orderService.getOrderById(1L);

        Assertions.assertEquals(mockResponseOrder, response);
    }

    @Test
    void shouldThrowNotFoundWhenThereIsNoOrderWithSpecifiedId() {
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void shouldGetOrdersByUser() {
        Mockito.when(orderRepository.findAllByUserId(mockUserId)).thenReturn(List.of(mockOrder));
        Mockito.when(orderMapper.modelToResponse(mockOrder)).thenReturn(mockResponseOrder);

        List<ResponseOrderDTO> response = orderService.getOrdersByUser(mockUserId);

        Assertions.assertEquals(List.of(mockResponseOrder), response);
    }

    @Test
    void shouldGetOrderByUserAndId() {
        Mockito.when(orderRepository.findByUserIdAndId(mockUserId, 1L)).thenReturn(Optional.of(mockOrder));
        Mockito.when(orderMapper.modelToResponse(mockOrder)).thenReturn(mockResponseOrder);

        ResponseOrderDTO response = orderService.getOrderByUserAndId(mockUserId, 1L);

        Assertions.assertEquals(mockResponseOrder, response);
    }

    @Test
    void shouldThrowNotFoundWhenUserDoesNotHaveOrderWithSpecifiedId() {
        Mockito.when(orderRepository.findByUserIdAndId(mockUserId, 1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> orderService.getOrderByUserAndId(mockUserId, 1L));
    }

    @Test
    void shouldSubmitOrderWithAsaasPaymentDataAndPendingStatus() {
        Order mappedOrder = OrderFactory.getMockOfOrder();
        mappedOrder.setId(null);
        mockSubmitOrderFlow(mappedOrder);

        ResponseOrderDTO response = orderService.submitOrder(mockSubmitOrder, mockUserId, mockAsaasCustomerId);

        ArgumentCaptor<Order> savedOrder = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(orderRepository).save(savedOrder.capture());

        Assertions.assertEquals(mockResponseOrder, response);
        Assertions.assertEquals("PENDING", savedOrder.getValue().getStatus());
        Assertions.assertEquals(OrderFactory.getMockOfAsaasOrderId(), savedOrder.getValue().getAsaasOrderId());
        Assertions.assertEquals(OrderFactory.getMockOfInvoiceUrl(), savedOrder.getValue().getInvoiceUrl());
        Assertions.assertEquals(160.0, savedOrder.getValue().getPrice());
    }

    @Test
    void shouldSendRequestedQuantitiesAndUserToTheOrder() {
        Order mappedOrder = OrderFactory.getMockOfOrder();
        mockSubmitOrderFlow(mappedOrder);

        orderService.submitOrder(mockSubmitOrder, mockUserId, mockAsaasCustomerId);

        ArgumentCaptor<RequestOrderDTO> requestOrder = ArgumentCaptor.forClass(RequestOrderDTO.class);
        Mockito.verify(orderMapper).requestToModel(requestOrder.capture());

        Assertions.assertEquals(mockUserId, requestOrder.getValue().getUserId());
        Assertions.assertEquals(OrderFactory.getMockOfQuantity(), requestOrder.getValue().getItems().getFirst().getQuantity());
    }

    @Test
    void shouldLinkItemsToTheOrderBeforeSaving() {
        Order mappedOrder = OrderFactory.getMockOfOrder();
        mockSubmitOrderFlow(mappedOrder);

        orderService.submitOrder(mockSubmitOrder, mockUserId, mockAsaasCustomerId);

        mappedOrder.getItems().forEach(item -> Assertions.assertSame(mappedOrder, item.getOrder()));
    }

    @Test
    void shouldGeneratePaymentForTheAsaasCustomerWithTheOrderValue() {
        Order mappedOrder = OrderFactory.getMockOfOrder();
        mockSubmitOrderFlow(mappedOrder);

        orderService.submitOrder(mockSubmitOrder, mockUserId, mockAsaasCustomerId);

        ArgumentCaptor<GeneratePaymentBodyDTO> paymentBody = ArgumentCaptor.forClass(GeneratePaymentBodyDTO.class);
        Mockito.verify(asaasClient).generatePayment(paymentBody.capture());

        Assertions.assertEquals(mockAsaasCustomerId, paymentBody.getValue().getCustomer());
        Assertions.assertEquals(BigDecimal.valueOf(mappedOrder.getPrice()), paymentBody.getValue().getValue());
    }

    @Test
    void shouldThrowNotFoundWhenSomeProductIsNotInCatalog() {
        Mockito.when(orderMapper.submitToRequest(mockSubmitOrder)).thenReturn(OrderFactory.getMockOfRequestOrder());
        Mockito.when(productClient.getProductsByIds(Mockito.anyList())).thenReturn(List.of());
        Mockito.when(itemMapper.catalogToModel(Mockito.<List<CatalogItemDTO>>any())).thenReturn(List.of());

        Assertions.assertThrows(NotFoundException.class,
                () -> orderService.submitOrder(mockSubmitOrder, mockUserId, mockAsaasCustomerId));

        Mockito.verify(asaasClient, Mockito.never()).generatePayment(Mockito.any());
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldChangeOrderStatusToPaidWhenReceivingWebHook() {
        Mockito.when(orderRepository.findByAsaasOrderId(OrderFactory.getMockOfAsaasOrderId()))
                .thenReturn(Optional.of(mockOrder));

        orderService.changeOrderStatus(OrderFactory.getMockOfWebHookBody());

        ArgumentCaptor<Order> savedOrder = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(orderRepository).save(savedOrder.capture());
        Assertions.assertEquals("PAID", savedOrder.getValue().getStatus());
    }

    @Test
    void shouldThrowNotFoundWhenWebHookOrderDoesNotExist() {
        Mockito.when(orderRepository.findByAsaasOrderId(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> orderService.changeOrderStatus(OrderFactory.getMockOfWebHookBody()));

        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any());
    }

    private void mockSubmitOrderFlow(Order mappedOrder) {
        CatalogItemDTO catalogItem = OrderFactory.getMockOfCatalogItem();
        Item item = OrderFactory.getMockOfItem();

        Mockito.when(orderMapper.submitToRequest(mockSubmitOrder)).thenReturn(OrderFactory.getMockOfRequestOrder());
        Mockito.when(productClient.getProductsByIds(Mockito.anyList())).thenReturn(List.of(catalogItem));
        Mockito.when(itemMapper.catalogToModel(Mockito.<List<CatalogItemDTO>>any())).thenReturn(new ArrayList<>(List.of(item)));
        Mockito.when(orderMapper.requestToModel(Mockito.any(RequestOrderDTO.class))).thenReturn(mappedOrder);
        Mockito.when(paymentMapper.orderBodyToBuiltPayment(mappedOrder)).thenReturn(OrderFactory.getMockOfGeneratePaymentBody());
        Mockito.when(asaasClient.generatePayment(Mockito.any(GeneratePaymentBodyDTO.class))).thenReturn(OrderFactory.getMockOfGeneratedPayment());
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(orderMapper.modelToResponse(Mockito.any(Order.class))).thenReturn(mockResponseOrder);
    }
}
