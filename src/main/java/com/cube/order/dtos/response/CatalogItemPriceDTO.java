package com.cube.order.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CatalogItemPriceDTO {

    private Double standard;

    private Double sale;

}
