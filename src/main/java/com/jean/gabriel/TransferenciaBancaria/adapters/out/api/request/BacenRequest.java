package com.jean.gabriel.TransferenciaBancaria.adapters.out.api.request;

import java.math.BigDecimal;

public record BacenRequest(String codigoContaRemetente, String codigoContaDestinatario, BigDecimal valorTransferido) {
}
