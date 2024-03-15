package com.jean.gabriel.TransferenciaBancaria.adapters.out.api;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.request.TransferenciaBancariaRequest;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.exception.ErroAoNotificarBacenException;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.request.BacenRequest;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.NotificarBacenAdapterOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.util.UUID;

import static com.jean.gabriel.TransferenciaBancaria.utils.ConstantsRoutes.ROTA_BACEN;
import static com.jean.gabriel.TransferenciaBancaria.utils.MensagensLogsEnum.*;

@Component
public class NotificarBacen implements NotificarBacenAdapterOut {

    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(NotificarBacen.class);

    public NotificarBacen(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public UUID notificarBacen(String idConta, TransferenciaBancariaRequest request) {
        try {
            log.info(INICIO_NOTIFICAR_BACEN.getMsg(), idConta);

            RetryBackoffSpec retrySpec = Retry.backoff(4, Duration.ofSeconds(3))
                    .filter(error -> error instanceof WebClientResponseException &&
                            ((WebClientResponseException) error).getStatusCode() == HttpStatus.TOO_MANY_REQUESTS)
                    .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> new ErroAoNotificarBacenException("Erro ao notificar o Bacen"));

            return webClient.post().uri(ROTA_BACEN)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(new BacenRequest(idConta, request.getIdContaDestinatario(), request.getValorTransferencia())))
                    .retrieve()
                    .bodyToMono(UUID.class)
                    .retryWhen(retrySpec)
                    .block();

        } catch (Exception e) {
            log.error(ERRO_NOTIFICAR_BACEN.getMsg(), idConta);
            throw new ErroAoNotificarBacenException("Erro notificar bacen");
        } finally {
            log.error(FIM_NOTIFICAR_BACEN.getMsg(), idConta);
        }
    }

}
