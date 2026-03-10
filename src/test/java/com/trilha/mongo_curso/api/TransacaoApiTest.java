package com.trilha.mongo_curso.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilha.mongo_curso.dto.TransacaoRequest;
import com.trilha.mongo_curso.dto.TransacaoResponse;
import com.trilha.mongo_curso.repository.TransacaoRepository;
import com.trilha.mongo_curso.service.TransacaoService;
import com.trilha.mongo_curso.stub.TransacaoRequestStub;
import com.trilha.mongo_curso.stub.TransacaoResponseStub;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TransacaoApiTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @InjectMocks
    private TransacaoService service;

    @Mock
    private TransacaoRepository repository;


    @Test
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
}
