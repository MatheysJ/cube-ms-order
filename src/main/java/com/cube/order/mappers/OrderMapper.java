package com.cube.order.mappers;

import com.cube.order.dtos.request.RequestOrderDTO;
import com.cube.order.dtos.request.SubmitOrderDTO;
import com.cube.order.dtos.response.ResponseOrderDTO;
import com.cube.order.models.Order;
import com.cube.order.utils.OrderUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = OrderUtils.class)
public interface OrderMapper {

    ResponseOrderDTO modelToResponse(Order order);

    @Mapping(source = "items", target = "price", qualifiedByName = "calculateOrderValue")
    Order requestToModel(RequestOrderDTO requestOrderDTO);

    RequestOrderDTO submitToRequest(SubmitOrderDTO submitOrderDTO);
}
