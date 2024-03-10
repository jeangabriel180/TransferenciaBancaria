package com.jean.gabriel.TransferenciaBancaria.core.ports.in;

import java.math.BigDecimal;

public interface ConsultarSaldoPorIdContaAdapterIn {

    BigDecimal executar(String idConta);
}
