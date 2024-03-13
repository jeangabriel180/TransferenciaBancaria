package com.jean.gabriel.TransferenciaBancaria.factory;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.api.response.ClienteResponse;

import java.util.UUID;

public class ClienteFactory {

    public static ClienteResponse gerarClienteResponse() {
        return new ClienteResponse(
                UUID.fromString("148c5cc1-e40a-4549-adc4-b54f0cc4b446"),
                "João da Silva"
        );
    }
}
