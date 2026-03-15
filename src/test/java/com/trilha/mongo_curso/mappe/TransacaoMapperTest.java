package com.trilha.mongo_curso.mappe;

import com.trilha.mongo_curso.dto.TransacaoRequest;
import com.trilha.mongo_curso.dto.TransacaoResponse;
import com.trilha.mongo_curso.model.Transacao;
import com.trilha.mongo_curso.enumerated.StatusTransacao;
import com.trilha.mongo_curso.enumerated.TipoTransacao;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class TransacaoMapperTest {

    @Test
    void toTransacao_shouldMapContaIdAndValor() {
        // Arrange
        TransacaoRequest req = new TransacaoRequest();
        req.setContaId("abc-123");
        req.setValor(new BigDecimal("250.50"));

        // Act
        Transacao t = TransacaoMapper.toTransacao(req);

        // Assert
        assertNotNull(t, "Transacao não deve ser nula");
        assertEquals("abc-123", t.getContaId(), "ContaId deve ser mapeado");
        assertEquals(0, new BigDecimal("250.50").compareTo(t.getValor()), "Valor deve ser mapeado corretamente");
    }

    @Test
    void toResponse_shouldMapAllFields() {
        // Arrange
        Transacao t = new Transacao();
        t.setTipoTransacao(TipoTransacao.PIX);
        t.setValor(new BigDecimal("99.99"));
        Instant now = Instant.now();
        t.setDataHora(now);
        t.setStatusTransacao(StatusTransacao.APROVADA);

        // Act
        TransacaoResponse resp = TransacaoMapper.toResponse(t);

        // Assert
        assertNotNull(resp, "Response não deve ser nulo");
        assertEquals(TipoTransacao.PIX, resp.getTipoTransacao(), "TipoTransacao deve ser mapeado");
        assertEquals(0, new BigDecimal("99.99").compareTo(resp.getValor()), "Valor deve ser mapeado");
        assertEquals(now, resp.getDataHora(), "DataHora deve ser mapeada");
        assertEquals(StatusTransacao.APROVADA, resp.getStatusTransacao(), "Status deve ser mapeado");
    }
}
