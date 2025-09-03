package com.cube.order.utils;

import com.cube.order.models.Item;
import org.mapstruct.Named;

import java.util.List;

public class OrderUtils {

    @Named("calculateOrderValue")
    public static Double getOrderValue(List<Item> items) {
        return items.stream()
                .map(item -> item.getPrice() * item.getQuantity())
                .reduce(0.0, Double::sum);

    }

}
