package com.cube.order.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrderDTO {

    private String id;

    private String userId;

    private Double price;

    private Object items;

}
