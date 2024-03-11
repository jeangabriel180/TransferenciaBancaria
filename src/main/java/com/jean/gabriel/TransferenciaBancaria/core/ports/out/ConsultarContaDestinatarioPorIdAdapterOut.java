package com.jean.gabriel.TransferenciaBancaria.core.ports.out;

import com.jean.gabriel.TransferenciaBancaria.core.domain.Conta;

public interface ConsultarContaDestinatarioPorIdAdapterOut {
    Conta consultarContaPorId(String idConta);
}
