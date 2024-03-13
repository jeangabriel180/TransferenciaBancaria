package com.jean.gabriel.transferenciaBancaria.request;

import java.math.BigDecimal;

public record BacenRequest(String codigoContaRemetente, String codigoContaDestinatario, BigDecimal valorTransferido) {
}
