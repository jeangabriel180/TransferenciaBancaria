package com.jean.gabriel.TransferenciaBancaria.adapters.in.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransferenciaBancariaRequest(String idContaDestinatario, BigDecimal valorTransferencia) {
}
