package com.trilha.mongo_curso.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EspacoTempo {

    private Instant inicio;
    private Instant fim;

}
