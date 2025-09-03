package com.cube.order.clients.product;

import com.cube.order.dtos.response.CatalogItemDTO;
import com.cube.order.dtos.request.RequestItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "cube-ms-product")
public interface ProductClient {

    @RequestMapping(method = RequestMethod.POST, value = "/v1/product/list")
    List<CatalogItemDTO> getProductsByIds(@RequestBody List<RequestItemDTO> ids);

}
