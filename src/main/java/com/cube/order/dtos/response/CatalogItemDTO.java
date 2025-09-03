package com.cube.order.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogItemDTO {
    private String id;

    private String name;

    private String image;

    private String summary;

    private String description;

    private String categoryId;

    private CatalogItemPriceDTO price;

    private Map<String, Object> options;

}
