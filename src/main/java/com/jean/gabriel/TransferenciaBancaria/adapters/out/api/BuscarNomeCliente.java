package com.jean.gabriel.TransferenciaBancaria.adapters.out.api;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.exception.ErroAoBuscarNomeClienteException;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.response.ClienteResponse;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.BuscarNomeClienteAdapterOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

import static com.jean.gabriel.TransferenciaBancaria.utils.MensagensLogsEnum.*;

@Component
public class BuscarNomeCliente implements BuscarNomeClienteAdapterOut {

    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(BuscarNomeCliente.class);

    public BuscarNomeCliente(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public String buscarNomeCliente(UUID idCliente) {
        try {
            //Chamada mockada via ferramenta Mockoon
            log.info(INICIO_BUSCAR_NOME_CLIENTE.getMsg(), idCliente);
            ClienteResponse cliente = webClient.get().uri("http://localhost:3000/cadastro/" +
                    idCliente).retrieve().bodyToMono(ClienteResponse.class).block();
            if (cliente != null) {
                return cliente.nomeCliente();
            } else {
                throw new ErroAoBuscarNomeClienteException();
            }
        } catch (Exception e) {
            log.error(ERRO_BUSCAR_NOME_CLIENTE.getMsg(), idCliente);
            throw new ErroAoBuscarNomeClienteException("Erro ao buscar cliente", e);
        } finally {
            log.error(FIM_BUSCAR_NOME_CLIENTE.getMsg(), idCliente);
        }
    }
}
