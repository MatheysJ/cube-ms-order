package com.cube.order.dtos.request;

import com.cube.order.models.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestOrderDTO {

    private String userId;

    private AddressDTO address;

    private List<Item> items;

}
