package com.cube.order.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestItemDTO {

    private String id;

    private Map<String, String> options;

    private Integer quantity;

}
