package com.jean.gabriel.TransferenciaBancaria.adapter.out.api;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.request.TransferenciaBancariaRequest;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.NotificarBacen;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.exception.ErroAoNotificarBacenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class NotificarBacenTest {

    @Mock
    private WebClient webClient;

    private NotificarBacen notificarBacen;

    @Mock
    private WebClient.RequestBodyUriSpec uriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @BeforeEach
    void setUp() {
        notificarBacen = new NotificarBacen(webClient);
    }

    @Test
    void deveNotificarBacenComSucesso() {
        String idConta = "12345";
        TransferenciaBancariaRequest request = new TransferenciaBancariaRequest("54321",
                new BigDecimal("100.0"));

        when(uriSpec.uri("http://localhost:3001/bacen")).thenReturn(uriSpec);
        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        doReturn(Mono.just(UUID.fromString("325d5dc1-1699-4258-bd6e-ae3cea113fe8"))).when(responseSpec).bodyToMono(UUID.class);

        UUID idRetornoBacen = notificarBacen.notificarBacen(idConta, request);

        assertNotNull(idRetornoBacen);
        assertEquals(UUID.fromString("325d5dc1-1699-4258-bd6e-ae3cea113fe8"), idRetornoBacen);
    }

    @Test
    void deveLancarExcecaoAoNotificarBacen() {
        String idConta = "12345";
        TransferenciaBancariaRequest request = new TransferenciaBancariaRequest("54321",
                new BigDecimal("100.0"));

        when(uriSpec.uri("http://localhost:3001/bacen")).thenReturn(uriSpec);
        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenThrow(new RuntimeException("mock excecao"));

        assertThrows(ErroAoNotificarBacenException.class,
                () -> notificarBacen.notificarBacen(idConta, request));
    }

}
