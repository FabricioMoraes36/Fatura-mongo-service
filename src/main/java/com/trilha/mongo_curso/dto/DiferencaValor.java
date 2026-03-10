package com.trilha.mongo_curso.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DiferencaValor {

    public DiferencaValor() {
    }

    public DiferencaValor(BigDecimal valorInicial, BigDecimal valorFinal) {
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
    }

    private BigDecimal valorInicial;
    private BigDecimal valorFinal;
}
