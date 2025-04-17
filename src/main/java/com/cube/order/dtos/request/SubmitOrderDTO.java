package com.cube.order.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitOrderDTO {

    @Valid
    @NotNull
    private AddressDTO address;

    @Valid
    @NotNull
    /*TODO: Remove comment @NotEmpty*/
    private List<RequestItemDTO> items;

}
