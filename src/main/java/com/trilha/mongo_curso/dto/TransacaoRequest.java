package com.trilha.mongo_curso.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TransacaoRequest {

    private String contaId;
    private BigDecimal valor;
}

