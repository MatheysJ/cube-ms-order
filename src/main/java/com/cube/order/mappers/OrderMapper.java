package com.cube.order.mappers;

import com.cube.order.dtos.request.RequestOrderDTO;
import com.cube.order.dtos.request.SubmitOrderDTO;
import com.cube.order.dtos.response.ResponseOrderDTO;
import com.cube.order.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    ResponseOrderDTO modelToResponse(Order order);

    Order requestToModel(RequestOrderDTO requestOrderDTO);

    RequestOrderDTO submitToRequest(SubmitOrderDTO submitOrderDTO);
}
