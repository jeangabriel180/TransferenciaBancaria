package com.jean.gabriel.TransferenciaBancaria.adapter.out.api;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.BuscarNomeCliente;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.exception.ErroAoBuscarNomeClienteException;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.response.ClienteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.jean.gabriel.TransferenciaBancaria.factory.ClienteFactory.gerarClienteResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarNomeClienteTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec headerSpec;

    @Mock
    private WebClient.RequestHeadersUriSpec headersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private BuscarNomeCliente buscarNomeCliente;

    @BeforeEach
    void setUp() {
        buscarNomeCliente = new BuscarNomeCliente(webClient);
    }

    @Test
    void buscarNomeClienteComSucesso() {
        UUID idCliente = UUID.randomUUID();
        ClienteResponse clienteResponse = gerarClienteResponse();

        when(webClient.get()).thenReturn(headersUriSpec);
        when(headersUriSpec.uri("http://localhost:3000/cadastro/" + idCliente)).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(responseSpec);
        doReturn(Mono.just(clienteResponse)).when(responseSpec).bodyToMono(ClienteResponse.class);

        String nomeCliente = buscarNomeCliente.buscarNomeCliente(idCliente);
        assertEquals("João da Silva", nomeCliente);
    }

    @Test
    void buscarNomeClienteNaoEncontrado() {
        UUID idCliente = UUID.randomUUID();

        when(webClient.get()).thenReturn(headersUriSpec);
        when(headersUriSpec.uri("http://localhost:3000/cadastro/" + idCliente)).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(responseSpec);
        doReturn(Mono.empty()).when(responseSpec).bodyToMono(ClienteResponse.class);

        assertThrows(ErroAoBuscarNomeClienteException.class,
                () -> buscarNomeCliente.buscarNomeCliente(idCliente));
    }

    @Test
    void deveLancarExcecaoAoBuscarNomeCliente() {
        UUID idCliente = UUID.randomUUID();

        when(webClient.get()).thenReturn(headersUriSpec);
        when(headersUriSpec.uri("http://localhost:3000/cadastro/" + idCliente)).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(responseSpec).thenThrow(new RuntimeException(""));
        doReturn(Mono.empty()).when(responseSpec).bodyToMono(ClienteResponse.class);

        assertThrows(ErroAoBuscarNomeClienteException.class,
                () -> buscarNomeCliente.buscarNomeCliente(idCliente));
    }
}