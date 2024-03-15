package com.jean.gabriel.TransferenciaBancaria.adapters.out.api;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.exception.ErroAoBuscarNomeClienteException;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.response.ClienteResponse;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.BuscarNomeClienteAdapterOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

import static com.jean.gabriel.TransferenciaBancaria.utils.ConstantsRoutes.ROTA_SALDO;
import static com.jean.gabriel.TransferenciaBancaria.utils.MensagensLogsEnum.*;

@Component
public class BuscarNomeCliente implements BuscarNomeClienteAdapterOut {

    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(BuscarNomeCliente.class);

    public BuscarNomeCliente(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    @Cacheable(value = "cacheCliente")
    public String buscarNomeCliente(UUID idCliente) {
        try {
            log.info(INICIO_BUSCAR_NOME_CLIENTE.getMsg(), idCliente);
            ClienteResponse cliente = webClient.get().uri(ROTA_SALDO +
                            idCliente).retrieve().bodyToMono(ClienteResponse.class)
                    .onErrorResume(throwable -> {
                        throw new ErroAoBuscarNomeClienteException("Falha ao recuperar nome cliente");
                    })
                    .block();
            if (cliente != null) {
                return cliente.nomeCliente();
            } else {
                throw new ErroAoBuscarNomeClienteException("cliente nao encontrado");
            }
        } catch (Exception e) {
            log.error(ERRO_BUSCAR_NOME_CLIENTE.getMsg(), idCliente);
            throw new ErroAoBuscarNomeClienteException(e.getMessage());
        } finally {
            log.error(FIM_BUSCAR_NOME_CLIENTE.getMsg(), idCliente);
        }
    }
}
