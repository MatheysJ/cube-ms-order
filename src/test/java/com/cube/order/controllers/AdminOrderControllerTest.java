package com.cube.order.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cube.order.clients.asaas.AsaasClient;
import com.cube.order.clients.product.ProductClient;
import com.cube.order.enums.ExceptionCode;
import com.cube.order.exceptions.NotFoundException;
import com.cube.order.factory.OrderFactory;
import com.cube.order.services.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(AdminOrderController.class)
class AdminOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private ProductClient productClient;

    @MockitoBean
    private AsaasClient asaasClient;

    @Test
    void shouldReturnOkWithAllOrders() throws Exception {
        Mockito.when(orderService.getAllOrders()).thenReturn(List.of(OrderFactory.getMockOfResponseOrder()));

        mockMvc.perform(get("/v1/admin/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldReturnOkWithOrderById() throws Exception {
        Mockito.when(orderService.getOrderById(1L)).thenReturn(OrderFactory.getMockOfResponseOrder());

        mockMvc.perform(get("/v1/admin/order/1")).andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenOrderDoesNotExist() throws Exception {
        Mockito.when(orderService.getOrderById(1L))
                .thenThrow(new NotFoundException(ExceptionCode.ORDER_WITH_ID_DOESNT_EXIST));

        mockMvc.perform(get("/v1/admin/order/1")).andExpect(status().isNotFound());
    }
}
