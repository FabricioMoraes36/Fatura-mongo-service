package com.trilha.mongo_curso.dto;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoRequest {

    private String contaId;
    private BigDecimal valor;
}

