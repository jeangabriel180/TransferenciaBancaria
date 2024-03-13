package com.jean.gabriel.TransferenciaBancaria.factory;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.response.TransferenciaBancariaResponse;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferenciaBancariaResponseFactory {

    public static TransferenciaBancariaResponse gerarTransferenciaResponse() {
        return new TransferenciaBancariaResponse(
                "Teste Nome",
                "69853245672",
                "63482050509",
                new BigDecimal("1000"),
                UUID.fromString("fc90ff57-6f18-4bdf-912c-968bebe5f386")
        );
    }
}
