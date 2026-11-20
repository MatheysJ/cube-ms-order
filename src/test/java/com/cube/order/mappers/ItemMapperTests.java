package com.cube.order.mappers;

import com.cube.order.dtos.response.CatalogItemDTO;
import com.cube.order.factory.OrderFactory;
import com.cube.order.models.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemMapperTests {

    private final ItemMapper itemMapper = new ItemMapperImpl();

    @Test
    void shouldMapCatalogItemUsingTheLowestPrice() {
        CatalogItemDTO catalogItem = OrderFactory.getMockOfCatalogItem();

        Item item = itemMapper.catalogToModel(catalogItem);

        Assertions.assertEquals(catalogItem.getId(), item.getId());
        Assertions.assertEquals(catalogItem.getName(), item.getName());
        Assertions.assertEquals(80.0, item.getPrice());
    }

    @Test
    void shouldMapEveryCatalogItemOfTheList() {
        List<Item> items = itemMapper.catalogToModel(List.of(
                OrderFactory.getMockOfCatalogItem(),
                OrderFactory.getMockOfCatalogItem()
        ));

        Assertions.assertEquals(2, items.size());
    }
}
