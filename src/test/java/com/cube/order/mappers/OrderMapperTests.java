package com.cube.order.mappers;

import com.cube.order.dtos.request.RequestOrderDTO;
import com.cube.order.enums.PaymentMethod;
import com.cube.order.models.Item;
import com.cube.order.models.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class OrderMapperTests {

    private final OrderMapper orderMapper = new OrderMapperImpl();

    @Test
    void shouldCalculateOrderPriceFromItems() {
        RequestOrderDTO requestOrder = RequestOrderDTO.builder()
                .userId("123")
                .billingType(PaymentMethod.PIX)
                .items(List.of(
                        Item.builder().id("a").price(80.0).quantity(2).build(),
                        Item.builder().id("b").price(15.0).quantity(1).build()
                ))
                .build();

        Order order = orderMapper.requestToModel(requestOrder);

        Assertions.assertEquals(175.0, order.getPrice());
        Assertions.assertEquals("123", order.getUserId());
        Assertions.assertEquals(PaymentMethod.PIX, order.getBillingType());
    }
}
