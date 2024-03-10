package com.jean.gabriel.TransferenciaBancaria.core.ports.out;

import java.math.BigDecimal;

public interface ConsultarSaldoPorIdContaAdapterOut {

    BigDecimal consultarSaldoPorIdConta(String idConta);
}
