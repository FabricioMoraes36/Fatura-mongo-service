package com.trilha.mongo_curso.stub;

import com.trilha.mongo_curso.dto.TransacaoRequest;

import java.math.BigDecimal;

public class TransacaoRequestStub {

    public static TransacaoRequest criarStub() {
        TransacaoRequest request = new TransacaoRequest();
        request.setContaId("conta-123");
        request.setValor(new BigDecimal("100.00"));
        return request;
    }
}
