package com.cube.order.dtos.request;

import com.cube.order.dtos.response.AsaasWebHookBodyPayment;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AsaasWebHookBody {

    private String id;

    private String event;

    private String dateCreated;

    private AsaasWebHookBodyPayment payment;

}
