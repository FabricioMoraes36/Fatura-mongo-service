package com.trilha.mongo_curso.service;

import com.trilha.mongo_curso.dto.TransacaoRequest;
import com.trilha.mongo_curso.dto.TransacaoResponse;
import com.trilha.mongo_curso.enumerated.StatusTransacao;
import com.trilha.mongo_curso.model.Transacao;
import com.trilha.mongo_curso.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @Mock
    private TransacaoRepository repository;

    private TransacaoService service;

    @BeforeEach
    void setup(){
        service = new TransacaoService(repository);
    }

    @Test
    void criarTransacao_shouldSetDataHoraAndSave() {
        // Arrange
        TransacaoRequest request = new TransacaoRequest();
        request.setContaId("c1");
        request.setValor(new BigDecimal("12.34"));

        Transacao savedFromRepo = new Transacao();
        savedFromRepo.setContaId("c1");
        savedFromRepo.setValor(new BigDecimal("12.34"));
        savedFromRepo.setDataHora(Instant.now());
        savedFromRepo.setStatusTransacao(StatusTransacao.PENDENTE);

        when(repository.save(any(Transacao.class))).thenReturn(savedFromRepo);

        // Act
        TransacaoResponse response = service.criarTransacao(request);

        // Assert
        assertNotNull(response, "Resposta não deve ser nula");
        assertEquals(0, new BigDecimal("12.34").compareTo(response.getValor()), "Valor retornado deve ser o esperado");

        ArgumentCaptor<Transacao> captor = ArgumentCaptor.forClass(Transacao.class);
        verify(repository, times(1)).save(captor.capture());
        Transacao captured = captor.getValue();
        assertEquals("c1", captured.getContaId(), "ContaId deve ser propagado ao salvar");
        assertNotNull(captured.getDataHora(), "DataHora deve ser setada antes do save");
    }

    @Test
    void buscarPaginado_shouldReturnMappedPage() {
        // Arrange
        Transacao t1 = new Transacao();
        t1.setContaId("conta1");
        t1.setValor(new BigDecimal("1"));
        Transacao t2 = new Transacao();
        t2.setContaId("conta1");
        t2.setValor(new BigDecimal("2"));

        Page<Transacao> pageFromRepo = new PageImpl<>(List.of(t1, t2), PageRequest.of(0,10), 2);
        when(repository.findByContaId(anyString(), any())).thenReturn(pageFromRepo);

        // Act
        Page<TransacaoResponse> responsePage = service.buscarPaginado("conta1",0,10);

        // Assert
        assertEquals(2, responsePage.getTotalElements(), "Total de elementos deve ser 2");
        assertEquals(2, responsePage.getContent().size(), "Conteúdo da página deve ter 2 itens");
        assertEquals(0, new BigDecimal("1").compareTo(responsePage.getContent().get(0).getValor()), "Primeiro valor deve ser mapeado corretamente");
    }

    @Test
    void buscarTodas_delegateToRepository() {
        // Arrange
        Transacao t = new Transacao();
        t.setValor(new BigDecimal("3"));
        when(repository.findAll()).thenReturn(List.of(t));

        // Act
        var result = service.buscarTodas();

        // Assert
        assertEquals(1, result.size(), "Deve retornar uma transacao");
        assertEquals(0, new BigDecimal("3").compareTo(result.get(0).getValor()), "Valor deve ser o mesmo do repo");
    }

}
