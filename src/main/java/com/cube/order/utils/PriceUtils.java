package com.cube.order.utils;

import com.cube.order.dtos.response.CatalogItemPriceDTO;
import org.mapstruct.Named;

public class PriceUtils {

    @Named("lowestPrice")
    public static Double getLowestPrice(CatalogItemPriceDTO price) {
        if (price == null) return null;

        Double standard = price.getStandard();
        Double sale = price.getSale();

        if (standard != null && sale != null) {
            return Math.min(standard, sale);
        }
        if (standard != null) {
            return standard;
        }
        return sale;
    }

}
