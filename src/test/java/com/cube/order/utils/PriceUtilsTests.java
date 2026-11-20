package com.cube.order.utils;

import com.cube.order.dtos.response.CatalogItemPriceDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PriceUtilsTests {

    @Test
    void shouldReturnSalePriceWhenItIsLowerThanStandard() {
        CatalogItemPriceDTO price = CatalogItemPriceDTO.builder().standard(100.0).sale(80.0).build();

        Assertions.assertEquals(80.0, PriceUtils.getLowestPrice(price));
    }

    @Test
    void shouldReturnStandardPriceWhenSaleIsHigher() {
        CatalogItemPriceDTO price = CatalogItemPriceDTO.builder().standard(100.0).sale(120.0).build();

        Assertions.assertEquals(100.0, PriceUtils.getLowestPrice(price));
    }

    @Test
    void shouldReturnStandardPriceWhenThereIsNoSale() {
        CatalogItemPriceDTO price = CatalogItemPriceDTO.builder().standard(100.0).build();

        Assertions.assertEquals(100.0, PriceUtils.getLowestPrice(price));
    }

    @Test
    void shouldReturnSalePriceWhenThereIsNoStandard() {
        CatalogItemPriceDTO price = CatalogItemPriceDTO.builder().sale(80.0).build();

        Assertions.assertEquals(80.0, PriceUtils.getLowestPrice(price));
    }

    @Test
    void shouldReturnNullWhenThereIsNoPrice() {
        Assertions.assertNull(PriceUtils.getLowestPrice(null));
    }
}
