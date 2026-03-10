package com.trilha.mongo_curso.model;

import com.trilha.mongo_curso.enumerated.StatusTransacao;
import com.trilha.mongo_curso.enumerated.TipoTransacao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Document("transacoes")
public class Transacao {

    public Transacao() {
    }

    public Transacao(String id, String contaId, BigDecimal valor, TipoTransacao tipoTransacao, StatusTransacao statusTransacao) {
        this.id = id;
        this.contaId = contaId;
        this.valor = valor;
        this.tipoTransacao = TipoTransacao.PIX;
        this.dataHora = Instant.now();
        this.statusTransacao = StatusTransacao.PENDENTE;
    }

    @Id
    private String id;
    private String contaId;
    private BigDecimal valor;
    private Instant dataHora;
    private TipoTransacao tipoTransacao;
    private StatusTransacao statusTransacao;

}
