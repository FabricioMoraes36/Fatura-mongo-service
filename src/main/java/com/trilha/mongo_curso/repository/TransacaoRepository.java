package com.trilha.mongo_curso.repository;

import com.trilha.mongo_curso.dto.DiferencaValor;
import com.trilha.mongo_curso.dto.EspacoTempo;
import com.trilha.mongo_curso.enumerated.StatusTransacao;
import com.trilha.mongo_curso.model.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends MongoRepository<Transacao,String> {

    List<Transacao> findByContaid(String id);
    List<Transacao> findByContaIdAndDataHoraBetween (String id, EspacoTempo tempo);
    List<Transacao> findByContaIdAndStatus (String id, StatusTransacao status);
    List<Transacao> findByContaIdAndValorGreaterThan (String id, BigDecimal valor);
    List<Transacao> findByContaIdAndValorBetween (String id, DiferencaValor diferencaValor);

    Page<Transacao> findByContaId(String id, Pageable pageable);

}
