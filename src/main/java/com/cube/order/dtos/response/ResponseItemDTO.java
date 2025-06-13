package com.cube.order.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseItemDTO {

    private String id;

    private String name;

    private String image;

    private String summary;

    private String description;

    private String categoryId;

    private Double price;

    private Integer quantity;

}
