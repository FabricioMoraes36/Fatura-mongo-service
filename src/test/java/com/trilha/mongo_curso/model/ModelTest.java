package com.trilha.mongo_curso.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    void diferencaValor_constructor_and_getters_setters() {
        // Arrange
        BigDecimal inicial = new BigDecimal("1");
        BigDecimal fim = new BigDecimal("10");

        // Act
        DiferencaValor d = new DiferencaValor(inicial, fim);

        // Assert
        assertEquals(0, inicial.compareTo(d.getValorInicial()));
        assertEquals(0, fim.compareTo(d.getValorFinal()));

        // Act (mutate)
        d.setValorInicial(new BigDecimal("2"));
        d.setValorFinal(new BigDecimal("20"));

        // Assert (after mutation)
        assertEquals(0, new BigDecimal("2").compareTo(d.getValorInicial()));
        assertEquals(0, new BigDecimal("20").compareTo(d.getValorFinal()));
    }

    @Test
    void espacoTempo_getters_setters_and_empty_constructor() {
        // Arrange
        EspacoTempo e = new EspacoTempo();
        Instant i1 = Instant.parse("2020-01-01T00:00:00Z");
        Instant i2 = Instant.parse("2020-01-02T00:00:00Z");

        // Act
        e.setInicio(i1);
        e.setFim(i2);

        // Assert
        assertEquals(i1, e.getInicio());
        assertEquals(i2, e.getFim());
    }

    @Test
    void transacao_secondary_constructor_sets_defaults() {
        // Arrange & Act
        Transacao t = new Transacao("id1", "conta-1", new BigDecimal("5.00"), null, null);

        // Assert
        assertNotNull(t.getDataHora(), "DataHora deve ser configurada no construtor");
        assertEquals("conta-1", t.getContaId());
        assertEquals(0, new BigDecimal("5.00").compareTo(t.getValor()));
        assertNotNull(t.getTipoTransacao(), "TipoTransacao padrão deve estar presente");
        assertNotNull(t.getStatusTransacao(), "StatusTransacao padrão deve estar presente");
    }
}
