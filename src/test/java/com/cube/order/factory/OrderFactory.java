package com.cube.order.factory;

import com.cube.order.dtos.internal.asaas.request.GeneratePaymentBodyDTO;
import com.cube.order.dtos.internal.asaas.response.GeneratedPaymentDTO;
import com.cube.order.dtos.request.AsaasWebHookBody;
import com.cube.order.dtos.request.RequestItemDTO;
import com.cube.order.dtos.request.RequestOrderDTO;
import com.cube.order.dtos.request.SubmitOrderDTO;
import com.cube.order.dtos.response.AsaasWebHookBodyPayment;
import com.cube.order.dtos.response.CatalogItemDTO;
import com.cube.order.dtos.response.CatalogItemPriceDTO;
import com.cube.order.dtos.response.ResponseOrderDTO;
import com.cube.order.enums.PaymentMethod;
import com.cube.order.models.Item;
import com.cube.order.models.Order;
import com.cube.order.utils.JsonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderFactory {
    static private final Long orderIdMock = 1L;
    static private final String userIdMock = "123";
    static private final String asaasCustomerIdMock = "cus_456";
    static private final String asaasOrderIdMock = "pay_789";
    static private final String invoiceUrlMock = "https://sandbox.asaas.com/i/pay_789";
    static private final String productIdMock = "product-1";
    static private final Double standardPriceMock = 100.0;
    static private final Double salePriceMock = 80.0;
    static private final Integer quantityMock = 2;

    static public String getMockOfUserId() {
        return userIdMock;
    }

    static public String getMockOfAsaasCustomerId() {
        return asaasCustomerIdMock;
    }

    static public String getMockOfAsaasOrderId() {
        return asaasOrderIdMock;
    }

    static public String getMockOfInvoiceUrl() {
        return invoiceUrlMock;
    }

    static public Integer getMockOfQuantity() {
        return quantityMock;
    }

    static public RequestItemDTO getMockOfRequestItem() {
        return RequestItemDTO.builder()
                .id(productIdMock)
                .quantity(quantityMock)
                .build();
    }

    static public SubmitOrderDTO getMockOfSubmitOrder() {
        return SubmitOrderDTO.builder()
                .billingType(PaymentMethod.PIX)
                .items(List.of(getMockOfRequestItem()))
                .build();
    }

    static public String getMockOfSubmitOrderAsJson() {
        return JsonUtils.toJson(getMockOfSubmitOrder());
    }

    static public String getMockOfInvalidSubmitOrderAsJson() {
        SubmitOrderDTO submitOrder = getMockOfSubmitOrder();
        submitOrder.setItems(List.of());

        return JsonUtils.toJson(submitOrder);
    }

    static public RequestOrderDTO getMockOfRequestOrder() {
        return RequestOrderDTO.builder()
                .billingType(PaymentMethod.PIX)
                .build();
    }

    static public CatalogItemPriceDTO getMockOfCatalogItemPrice() {
        return CatalogItemPriceDTO.builder()
                .standard(standardPriceMock)
                .sale(salePriceMock)
                .build();
    }

    static public CatalogItemDTO getMockOfCatalogItem() {
        return CatalogItemDTO.builder()
                .id(productIdMock)
                .name("Cubo mágico")
                .price(getMockOfCatalogItemPrice())
                .build();
    }

    static public Item getMockOfItem() {
        return Item.builder()
                .id(productIdMock)
                .name("Cubo mágico")
                .price(salePriceMock)
                .build();
    }

    static public Order getMockOfOrder() {
        Item item = getMockOfItem();
        item.setQuantity(quantityMock);

        return Order.builder()
                .id(orderIdMock)
                .userId(userIdMock)
                .billingType(PaymentMethod.PIX)
                .price(salePriceMock * quantityMock)
                .items(new ArrayList<>(List.of(item)))
                .build();
    }

    static public ResponseOrderDTO getMockOfResponseOrder() {
        return ResponseOrderDTO.builder()
                .id(String.valueOf(orderIdMock))
                .asaasOrderId(asaasOrderIdMock)
                .price(salePriceMock * quantityMock)
                .billingType(PaymentMethod.PIX)
                .invoiceUrl(invoiceUrlMock)
                .status("PENDING")
                .build();
    }

    static public GeneratedPaymentDTO getMockOfGeneratedPayment() {
        return GeneratedPaymentDTO.builder()
                .id(asaasOrderIdMock)
                .value(BigDecimal.valueOf(salePriceMock * quantityMock))
                .invoiceUrl(invoiceUrlMock)
                .build();
    }

    static public GeneratePaymentBodyDTO getMockOfGeneratePaymentBody() {
        return new GeneratePaymentBodyDTO();
    }

    static public AsaasWebHookBody getMockOfWebHookBody() {
        return AsaasWebHookBody.builder()
                .id("evt_1")
                .event("PAYMENT_RECEIVED")
                .payment(AsaasWebHookBodyPayment.builder().id(asaasOrderIdMock).build())
                .build();
    }

    static public String getMockOfWebHookBodyAsJson() {
        return JsonUtils.toJson(getMockOfWebHookBody());
    }
}
