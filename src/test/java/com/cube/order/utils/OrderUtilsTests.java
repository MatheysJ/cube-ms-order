package com.cube.order.utils;

import com.cube.order.models.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class OrderUtilsTests {

    @Test
    void shouldSumPriceTimesQuantityOfEveryItem() {
        List<Item> items = List.of(
                Item.builder().price(10.0).quantity(2).build(),
                Item.builder().price(5.5).quantity(3).build()
        );

        Assertions.assertEquals(36.5, OrderUtils.getOrderValue(items));
    }

    @Test
    void shouldReturnZeroWhenOrderHasNoItems() {
        Assertions.assertEquals(0.0, OrderUtils.getOrderValue(List.of()));
    }
}
