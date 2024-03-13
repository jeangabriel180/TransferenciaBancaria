package com.jean.gabriel.TransferenciaBancaria.adapters.in.response;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferenciaBancariaResponse(String nomeCliente, String numeroContaRemetente, String numeroContaDestinatario,
                                            BigDecimal valorTransferido, UUID codigoBacenTransferencia) {
}
