package com.cube.order.dtos.internal;

import com.cube.order.dtos.request.AddresseDTO;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String street;

    private String houseNumber;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String preferred;

    @Embedded
    private AddresseDTO addressee;

}
