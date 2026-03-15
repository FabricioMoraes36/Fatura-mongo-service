package com.trilha.mongo_curso.mapper;

import com.trilha.mongo_curso.dto.TransacaoRequest;
import com.trilha.mongo_curso.dto.TransacaoResponse;
import com.trilha.mongo_curso.enumerated.StatusTransacao;
import com.trilha.mongo_curso.enumerated.TipoTransacao;
import com.trilha.mongo_curso.model.Transacao;

public class TransacaoMapper {

    public static TransacaoResponse toResponse(Transacao transacao){
        TransacaoResponse response = new TransacaoResponse();
        response.setTipoTransacao(transacao.getTipoTransacao());
        response.setValor(transacao.getValor());
        response.setDataHora(transacao.getDataHora());
        response.setStatusTransacao(transacao.getStatusTransacao());
        return response;

    }

    public static Transacao toTransacao(TransacaoRequest request) {
        Transacao transacao = new Transacao();
        transacao.setContaId(request.getContaId());
        transacao.setValor(request.getValor());
        transacao.setTipoTransacao(TipoTransacao.PIX);
        transacao.setStatusTransacao(StatusTransacao.PENDENTE);
        return transacao;
    }
}
