package com.trilha.mongo_curso.controller;

import com.trilha.mongo_curso.dto.TransacaoRequest;
import com.trilha.mongo_curso.dto.TransacaoResponse;
import com.trilha.mongo_curso.enumerated.StatusTransacao;
import com.trilha.mongo_curso.model.DiferencaValor;
import com.trilha.mongo_curso.model.EspacoTempo;
import com.trilha.mongo_curso.service.TransacaoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<TransacaoResponse> criarTransacao(@RequestBody TransacaoRequest request){
        TransacaoResponse response = transacaoService.criarTransacao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/todas")
    public List<TransacaoResponse> transacoes(){
        return transacaoService.buscarTodas();
    }

    @GetMapping("/{id}")
    public List<TransacaoResponse> transacoesPorId(@PathVariable String id){
        return transacaoService.buscarPorContaId(id);
    }

    @PostMapping("/tempo/{id}")
    public List<TransacaoResponse> transacoesPorIdEEspacoDeTempo(
            @PathVariable String id,
            @RequestBody EspacoTempo tempo){
        return transacaoService.buscarPorContaIdEPeriodo(id, tempo);
    }

    @GetMapping("/status/{id}")
    public List<TransacaoResponse> transacaoPorIdPorStatus(
            @PathVariable String id,
            @RequestParam StatusTransacao status){
        return transacaoService.buscarPorContaIdEStatus(id, status);
    }

    @GetMapping("/valorMaior/{id}")
    public List<TransacaoResponse> transacaoPorContaIdEValorMaior(
            @PathVariable String id,
            @RequestParam BigDecimal valor){
        return transacaoService.buscarPorContaIdEValorMaior(id, valor);
    }

    @PostMapping("/valores/{id}")
    public List<TransacaoResponse> transacaoPorIdEEspacoValor(
            @PathVariable String id,
            @RequestBody DiferencaValor diferenca){
        return transacaoService.buscarPorContaIdEIntervaloValor(id, diferenca);
    }

    @GetMapping("/paginado/{id}")
    public Page<TransacaoResponse> transacaoPaginada(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho){
        return transacaoService.buscarPaginado(id, pagina, tamanho);
    }
}
