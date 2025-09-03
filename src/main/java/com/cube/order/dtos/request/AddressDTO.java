package com.cube.order.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String street;

    private String houseNumber;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String name;

    private String preferred;

    private AddresseDTO addressee;

}
