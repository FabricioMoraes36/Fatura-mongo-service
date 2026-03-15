package com.trilha.mongo_curso.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EspacoTempo {

    private Instant inicio;
    private Instant fim;

    public EspacoTempo(Instant inicio, Instant fim) {
        this.inicio = inicio;
        this.fim = fim;
    }
}
