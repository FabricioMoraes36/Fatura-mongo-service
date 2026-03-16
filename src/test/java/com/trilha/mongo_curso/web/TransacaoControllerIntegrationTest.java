package com.trilha.mongo_curso.web;


import com.trilha.mongo_curso.dto.TransacaoRequest;
import com.trilha.mongo_curso.dto.TransacaoResponse;
import com.trilha.mongo_curso.repository.TransacaoRepository;
import com.trilha.mongo_curso.stub.TransacaoRequestStub;
import com.trilha.mongo_curso.stub.TransacaoResponseStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        webEnvironment = RANDOM_PORT
)


@AutoConfigureRestTestClient
public class TransacaoControllerIntegrationTest {

    @Container
    @ServiceConnection
    static MongoDBContainer mongo = new MongoDBContainer(DockerImageName.parse("mongo:latest"))
            .withExposedPorts(27017);;

    @Autowired
    WebTestClient client;


    @Autowired
    TransacaoRepository transacaoRepository;


    void setup() {
        transacaoRepository.deleteAll();
    }

    @Test
    void deveCriarTransacao(){

        TransacaoRequest request = TransacaoRequestStub.criarStub();

        client.post()
                .uri("/transacoes/criar")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransacaoResponse.class)
                .value(transacaoRequest -> {
                    assertThat(transacaoRequest).isNotNull();
                    assertThat(transacaoRequest.getValor()).isEqualTo(new BigDecimal("100.00"));

                });
            long count= transacaoRepository.count();
            assertThat(count).isEqualTo(1);

    }
}
