package com.cube.order.clients;

import com.cube.order.models.Item;
import com.cube.order.dtos.request.RequestItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("cubeMsProduct")
public interface ProductClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/product")
    List<Item> getProductsByIds(List<RequestItemDTO> ids);

}
