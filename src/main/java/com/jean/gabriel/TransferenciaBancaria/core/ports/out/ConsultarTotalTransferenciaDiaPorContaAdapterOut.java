package com.jean.gabriel.TransferenciaBancaria.core.ports.out;

import java.math.BigDecimal;

public interface ConsultarTotalTransferenciaDiaPorContaAdapterOut {

    BigDecimal consultarTotalTransferenciaDia(String idConta);

}
