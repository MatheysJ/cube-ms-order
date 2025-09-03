package com.cube.order.mappers;

import com.cube.order.dtos.response.CatalogItemDTO;
import com.cube.order.models.Item;
import com.cube.order.utils.PriceUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = PriceUtils.class)
public interface ItemMapper {

    @Mapping(source = "price", target = "price", qualifiedByName = "lowestPrice")
    Item catalogToModel(CatalogItemDTO catalogItemDTO);

    List<Item> catalogToModel(List<CatalogItemDTO> catalogItemDTO);

}
