package com.trilha.mongo_curso.dto;

import com.trilha.mongo_curso.enumerated.StatusTransacao;
import com.trilha.mongo_curso.enumerated.TipoTransacao;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoResponse {

    private BigDecimal valor;
    private Instant dataHora;
    private TipoTransacao tipoTransacao;
    private StatusTransacao statusTransacao;
}
