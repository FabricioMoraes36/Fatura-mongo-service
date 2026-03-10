package com.trilha.mongo_curso.service;

import com.trilha.mongo_curso.dto.TransacaoRequest;
import com.trilha.mongo_curso.dto.TransacaoResponse;
import com.trilha.mongo_curso.enumerated.StatusTransacao;
import com.trilha.mongo_curso.mappe.TransacaoMapper;
import com.trilha.mongo_curso.model.DiferencaValor;
import com.trilha.mongo_curso.model.EspacoTempo;
import com.trilha.mongo_curso.model.Transacao;
import com.trilha.mongo_curso.repository.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public TransacaoResponse criarTransacao(TransacaoRequest request){
        Transacao transacao = TransacaoMapper.toTransacao(request);
        transacao.setDataHora(Instant.now());

        Transacao salva = transacaoRepository.save(transacao);
        return TransacaoMapper.toResponse(salva);
    }

    public List<TransacaoResponse> buscarTodas(){
        return transacaoRepository.findAll()
                .stream()
                .map(TransacaoMapper::toResponse)
                .toList();
    }

    public List<TransacaoResponse> buscarPorContaId(String id){
        return transacaoRepository.findByContaId(id)
                .stream()
                .map(TransacaoMapper::toResponse)
                .toList();
    }

    public List<TransacaoResponse> buscarPorContaIdEPeriodo(String id, EspacoTempo tempo){
        return transacaoRepository.findByContaIdAndDataHoraBetween(id, tempo)
                .stream()
                .map(TransacaoMapper::toResponse)
                .toList();
    }

    public List<TransacaoResponse> buscarPorContaIdEStatus(String id, StatusTransacao status){
        return transacaoRepository.findByContaIdAndStatusTransacao(id, status)
                .stream()
                .map(TransacaoMapper::toResponse)
                .toList();
    }

    public List<TransacaoResponse> buscarPorContaIdEValorMaior(String id, BigDecimal valor){
        return transacaoRepository.findByContaIdAndValorGreaterThan(id, valor)
                .stream()
                .map(TransacaoMapper::toResponse)
                .toList();
    }

    public List<TransacaoResponse> buscarPorContaIdEIntervaloValor(String id, DiferencaValor diferenca){
        return transacaoRepository.findByContaIdAndValorBetween(id, diferenca)
                .stream()
                .map(TransacaoMapper::toResponse)
                .toList();
    }

    public Page<TransacaoResponse> buscarPaginado(String id, int pagina, int tamanho){
        Pageable page = PageRequest.of(pagina, tamanho);

        return transacaoRepository
                .findByContaId(id, page)
                .map(TransacaoMapper::toResponse);
    }

}
