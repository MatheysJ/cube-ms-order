package com.cube.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    ORDER_WITH_ID_DOESNT_EXIST("Ordem com id informado não existe"),
    USER_ORDER_WITH_ID_DOESNT_EXIST("Usuário não possui ordem com o id informado"),
    PRODUCT_WITH_ID_DOESNT_EXIST("Produto com id informado não existe"),
    UNKNOWN_ERROR("Erro desconhecido"),
    PRODUCT_NOT_FOUND("Desculpe, o produto está fora de estoque"),
    ASAAS_ORDER_WITH_ID_DOESNT_EXIST("Ordem com id do Asaas informado não existe");


    private final String message;
}
