package com.trilha.mongo_curso.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilha.mongo_curso.controller.TransacaoController;
import com.trilha.mongo_curso.dto.TransacaoRequest;
import com.trilha.mongo_curso.dto.TransacaoResponse;
import com.trilha.mongo_curso.enumerated.StatusTransacao;
import com.trilha.mongo_curso.model.DiferencaValor;
import com.trilha.mongo_curso.model.EspacoTempo;
import com.trilha.mongo_curso.repository.TransacaoRepository;
import com.trilha.mongo_curso.service.TransacaoService;
import com.trilha.mongo_curso.stub.TransacaoRequestStub;
import com.trilha.mongo_curso.stub.TransacaoResponseStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TransacaoApiTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TransacaoService service;

    @InjectMocks
    private TransacaoController controller;

    @Mock
    private TransacaoRepository repository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("TransacaoService - Cria transação no mongo")
    void emprestimoApi_criarTransacao() throws Exception {

        TransacaoRequest mockRequest = TransacaoRequestStub.criarStub();
        TransacaoResponse mockResponse = TransacaoResponseStub.criarStub();

        when(service.criarTransacao(any(TransacaoRequest.class)))
                .thenReturn(mockResponse);

        String payload = objectMapper.writeValueAsString(mockRequest);

        mockMvc.perform(
                        post("/transacoes/criar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().is2xxSuccessful());

        verify(service, times(1))
                .criarTransacao(any(TransacaoRequest.class));
    }

    @Test
    @DisplayName("TransacaoService - Busca transacoes por id da conta")
    void emprestimoApi_deveBuscarTransacoesPorContaId() throws Exception {

        TransacaoResponse mockResponse1 = TransacaoResponseStub.criarStub();
        TransacaoResponse mockResponse2 = TransacaoResponseStub.criarStub();

        List<TransacaoResponse> lista = List.of(mockResponse1, mockResponse2);

        when(service.buscarPorContaId(anyString()))
                .thenReturn(lista);

        mockMvc.perform(
                        get("/transacoes/conta1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].valor").exists())
                .andExpect(jsonPath("$[0].dataHora").exists());

        verify(service, times(1))
                .buscarPorContaId("conta1");
    }

    @Test
    @DisplayName("TransacaoService - Busca todas as transações no mongo")
    void emprestimoApi_deveBuscarTodasTransacoes() throws Exception {

        TransacaoResponse mockResponse1 = TransacaoResponseStub.criarStub();
        TransacaoResponse mockResponse2 = TransacaoResponseStub.criarStub();

        List<TransacaoResponse> lista = List.of(mockResponse1, mockResponse2);

        when(service.buscarTodas())
                .thenReturn(lista);

        mockMvc.perform(
                        get("/transacoes/todas"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].valor").exists())
                .andExpect(jsonPath("$[0].dataHora").exists());

        verify(service, times(1))
                .buscarTodas();
    }

    @Test
    @DisplayName("TransacaoService - Busca transações por id e por espaço de tempo")
    public void emprestimoApi_deveBuscarPorIdETempo() throws Exception {

        TransacaoResponse mockResponse1 = TransacaoResponseStub.criarStub();
        TransacaoResponse mockResponse2 = TransacaoResponseStub.criarStub();

        List<TransacaoResponse> lista = List.of(mockResponse1, mockResponse2);

        EspacoTempo tempo = new EspacoTempo(
                Instant.parse("1990-01-10T00:00:00Z"),
                Instant.parse("1991-02-12T00:00:00Z")
        );

        String payload = objectMapper.writeValueAsString(tempo);

        when(service.buscarPorContaIdEPeriodo(anyString(), any(EspacoTempo.class)))
                .thenReturn(lista);

        mockMvc.perform(
                        post("/transacoes/tempo/contaId1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].valor").exists())
                .andExpect(jsonPath("$[0].dataHora").exists());

        verify(service, times(1))
                .buscarPorContaIdEPeriodo(anyString(), any(EspacoTempo.class));
    }

    @Test
    @DisplayName("TransacaoService - Busca transações por id e por status")
    void emprestimoApi_deveBuscarPorIdEStatus() throws Exception {

        TransacaoResponse mockResponse1 = TransacaoResponseStub.criarStub();
        TransacaoResponse mockResponse2 = TransacaoResponseStub.criarStub();

        List<TransacaoResponse> lista = List.of(mockResponse1, mockResponse2);

        when(service.buscarPorContaIdEStatus(anyString(), any(StatusTransacao.class)))
                .thenReturn(lista);

        mockMvc.perform(
                        get("/transacoes/status/contaid1")
                                .param("status", "PENDENTE")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].valor").exists())
                .andExpect(jsonPath("$[0].dataHora").exists());

        verify(service, times(1))
                .buscarPorContaIdEStatus(anyString(), any(StatusTransacao.class));
    }

    @Test
    @DisplayName("TransacaoService - Busca transações por id e por valor maior que o determinado")
    void emprestimoApi_deveBuscarPorIdEValorMaior() throws Exception {

        TransacaoResponse mockResponse1 = TransacaoResponseStub.criarStub();
        TransacaoResponse mockResponse2 = TransacaoResponseStub.criarStub();

        List<TransacaoResponse> lista = List.of(mockResponse1, mockResponse2);

        when(service.buscarPorContaIdEValorMaior(anyString(), any(BigDecimal.class)))
                .thenReturn(lista);

        mockMvc.perform(
                        get("/transacoes/valorMaior/contaid1")
                                .param("valor", BigDecimal.valueOf(100).toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].valor").exists())
                .andExpect(jsonPath("$[0].dataHora").exists());

        verify(service, times(1))
                .buscarPorContaIdEValorMaior(anyString(), any(BigDecimal.class));
    }

    @Test
    @DisplayName("TransacaoService - Busca transações por id e por espaço de valor")
    public void emprestimoApi_deveBuscarPorIdEspacoValor() throws Exception {

        TransacaoResponse mockResponse1 = TransacaoResponseStub.criarStub();
        TransacaoResponse mockResponse2 = TransacaoResponseStub.criarStub();

        List<TransacaoResponse> lista = List.of(mockResponse1, mockResponse2);

        DiferencaValor valor = new DiferencaValor(
                BigDecimal.ONE,
                BigDecimal.TEN
        );

        String payload = objectMapper.writeValueAsString(valor);

        when(service.buscarPorContaIdEIntervaloValor(anyString(), any(DiferencaValor.class)))
                .thenReturn(lista);

        mockMvc.perform(
                        post("/transacoes/valores/contaId1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].valor").exists())
                .andExpect(jsonPath("$[0].dataHora").exists());

        verify(service, times(1))
                .buscarPorContaIdEIntervaloValor(anyString(), any(DiferencaValor.class));
    }

    @Test
    @DisplayName("TransacaoService - Busca transações por id e paginado")
    void emprestimoApi_deveBuscarPorIdEpaginado() throws Exception {

        TransacaoResponse mockResponse1 = TransacaoResponseStub.criarStub();
        TransacaoResponse mockResponse2 = TransacaoResponseStub.criarStub();

        List<TransacaoResponse> lista = List.of(mockResponse1, mockResponse2);

        Page<TransacaoResponse> page =
                new PageImpl<>(lista, PageRequest.of(0, 10), lista.size());

        when(service.buscarPaginado(anyString(), anyInt(), anyInt()))
                .thenReturn(page);


        mockMvc.perform(
                        get("/transacoes/paginado/contaid1")
                                .param("pagina", "0")
                                .param("tamanho", "10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].valor").exists())
                .andExpect(jsonPath("$.content[0].dataHora").exists());

        verify(service, times(1))
                .buscarPaginado(anyString(), anyInt(), anyInt());
    }
}
