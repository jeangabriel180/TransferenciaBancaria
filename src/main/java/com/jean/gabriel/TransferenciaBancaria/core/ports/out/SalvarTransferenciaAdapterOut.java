package com.jean.gabriel.TransferenciaBancaria.core.ports.out;

import com.jean.gabriel.TransferenciaBancaria.core.domain.Transferencia;

public interface SalvarTransferenciaAdapterOut {
    void salvar(Transferencia transferencia);
}
