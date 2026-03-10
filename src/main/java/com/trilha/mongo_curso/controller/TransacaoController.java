package com.trilha.mongo_curso.controller;

import com.trilha.mongo_curso.dto.DiferencaValor;
import com.trilha.mongo_curso.dto.EspacoTempo;
import com.trilha.mongo_curso.enumerated.StatusTransacao;
import com.trilha.mongo_curso.model.Transacao;
import com.trilha.mongo_curso.repository.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoRepository transacaoRepository;

    public TransacaoController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @PostMapping("/criar")
    public Transacao criarTransacao(@RequestBody Transacao transacao){
        return transacaoRepository.save(transacao);
    }

    @GetMapping("/todas")
    public List<Transacao> transacoes(){
        return transacaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public List<Transacao> transacoesPorId (@PathVariable String id){
        return transacaoRepository.findByContaid(id);
    }

    @GetMapping("/tempo/{id}")
    public List<Transacao> transacoesPorIdEEspacoDeTempo(@PathVariable String id, @RequestBody EspacoTempo tempo){
        return transacaoRepository.findByContaIdAndDataHoraBetween(id,tempo);
    }

    @GetMapping("/status/{id}")
    public List<Transacao> transacaoPorIdPorStatus (String id, StatusTransacao statusTransacao){
        return transacaoRepository.findByContaIdAndStatus(id,statusTransacao);
    }

    @GetMapping("/valorMaior/{id}")
    public List<Transacao> transacaoPorContaIdEValorMaior(String id, BigDecimal valor){
        return transacaoRepository.findByContaIdAndValorGreaterThan(id, valor);
    }

    @GetMapping("/valores/{id}")
    public List<Transacao> transacaoPorIdEEspacoValor(String id, DiferencaValor difrenca){
        return transacaoRepository.findByContaIdAndValorBetween(id, difrenca);
    }

    @GetMapping("/paginado/{id}")
    public Page<Transacao> transacaoPaginada(@PathVariable String id,@RequestParam(defaultValue = "0") int paginaInicio, @RequestParam(defaultValue = "10") int paginaTamanho){
        return transacaoRepository.findByContaId(id,paginaInicio,paginaTamanho);
    }
}
