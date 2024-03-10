package com.jean.gabriel.TransferenciaBancaria.adapters.out.api;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.request.TransferenciaBancariaRequest;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.exception.ErroAoBuscarNomeClienteException;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.exception.ErroAoNotificarBacenException;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.request.BacenRequest;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.NotificarBacenAdapterOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

import static com.jean.gabriel.TransferenciaBancaria.utils.MensagensLogsEnum.*;

@Component
public class NotificarBacen implements NotificarBacenAdapterOut {

    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(BuscarNomeCliente.class);

    public NotificarBacen(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public UUID notificarBacen(String idConta, TransferenciaBancariaRequest request) {
        try {
            //Chamada mockada via ferramenta Mockoon
            log.info(INICIO_NOTIFICAR_BACEN.getMsg(), idConta);
            return webClient.post().uri("http://localhost:3001/bacen").header(HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_JSON_VALUE).body(BodyInserters.fromValue(new BacenRequest(idConta,
                    request.idContaDestinatario(), request.valorTransferencia()))).retrieve().bodyToMono(UUID.class).block();
        } catch (Exception e) {
            log.error(ERRO_NOTIFICAR_BACEN.getMsg(), idConta);
            throw new ErroAoNotificarBacenException("Erro ao buscar cliente", e);
        } finally {
            log.error(FIM_NOTIFICAR_BACEN.getMsg(), idConta);
        }
    }

}
