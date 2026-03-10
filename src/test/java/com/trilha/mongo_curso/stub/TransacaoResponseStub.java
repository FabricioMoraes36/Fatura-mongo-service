package com.trilha.mongo_curso.stub;

import com.trilha.mongo_curso.dto.TransacaoRequest;
import com.trilha.mongo_curso.dto.TransacaoResponse;
import com.trilha.mongo_curso.enumerated.StatusTransacao;
import com.trilha.mongo_curso.enumerated.TipoTransacao;

import java.math.BigDecimal;
import java.time.Instant;

public class TransacaoResponseStub {

    public static TransacaoResponse criarStub() {
        TransacaoResponse response = new TransacaoResponse();
        response.setValor(new BigDecimal("100.00"));
        response.setDataHora(Instant.now());
        response.setTipoTransacao(TipoTransacao.PIX);
        response.setStatusTransacao(StatusTransacao.PENDENTE);
        return response;
    }
}
