package com.cube.order.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cube.order.clients.asaas.AsaasClient;
import com.cube.order.clients.product.ProductClient;
import com.cube.order.dtos.request.AsaasWebHookBody;
import com.cube.order.dtos.request.SubmitOrderDTO;
import com.cube.order.dtos.response.ResponseOrderDTO;
import com.cube.order.enums.ExceptionCode;
import com.cube.order.exceptions.NotFoundException;
import com.cube.order.factory.OrderFactory;
import com.cube.order.services.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private ProductClient productClient;

    @MockitoBean
    private AsaasClient asaasClient;

    private final ResponseOrderDTO mockResponseOrder = OrderFactory.getMockOfResponseOrder();
    private final String mockUserId = OrderFactory.getMockOfUserId();

    @Test
    void shouldReturnOkWithUserOrders() throws Exception {
        Mockito.when(orderService.getOrdersByUser(mockUserId)).thenReturn(List.of(mockResponseOrder));

        mockMvc.perform(
                get("/v1/order").header("customer_id", mockUserId)
        ).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(mockResponseOrder.getId()));
    }

    @Test
    void shouldReturnOkWithUserOrderById() throws Exception {
        Mockito.when(orderService.getOrderByUserAndId(mockUserId, 1L)).thenReturn(mockResponseOrder);

        mockMvc.perform(
                get("/v1/order/1").header("customer_id", mockUserId)
        ).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldReturnNotFoundWhenUserOrderDoesNotExist() throws Exception {
        Mockito.when(orderService.getOrderByUserAndId(mockUserId, 1L))
                .thenThrow(new NotFoundException(ExceptionCode.USER_ORDER_WITH_ID_DOESNT_EXIST));

        mockMvc.perform(
                get("/v1/order/1").header("customer_id", mockUserId)
        ).andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnCreatedWhenSubmittingAnOrder() throws Exception {
        Mockito.when(orderService.submitOrder(Mockito.any(SubmitOrderDTO.class), Mockito.eq(mockUserId), Mockito.anyString()))
                .thenReturn(mockResponseOrder);

        mockMvc.perform(
                post("/v1/order")
                        .header("customer_id", mockUserId)
                        .header("asaas_customer_id", OrderFactory.getMockOfAsaasCustomerId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OrderFactory.getMockOfSubmitOrderAsJson())
        ).andExpect(status().isCreated()).andExpect(jsonPath("$.invoiceUrl").value(OrderFactory.getMockOfInvoiceUrl()));
    }

    @Test
    void shouldReturnBadRequestWhenSubmittingAnOrderWithoutItems() throws Exception {
        mockMvc.perform(
                post("/v1/order")
                        .header("customer_id", mockUserId)
                        .header("asaas_customer_id", OrderFactory.getMockOfAsaasCustomerId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OrderFactory.getMockOfInvalidSubmitOrderAsJson())
        ).andExpect(status().isBadRequest());

        Mockito.verify(orderService, Mockito.never()).submitOrder(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldReturnOkWhenReceivingWebHook() throws Exception {
        mockMvc.perform(
                post("/v1/order/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OrderFactory.getMockOfWebHookBodyAsJson())
        ).andExpect(status().isOk());

        Mockito.verify(orderService).changeOrderStatus(Mockito.any(AsaasWebHookBody.class));
    }
}
