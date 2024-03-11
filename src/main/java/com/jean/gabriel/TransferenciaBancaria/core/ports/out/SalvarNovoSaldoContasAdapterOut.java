package com.jean.gabriel.TransferenciaBancaria.core.ports.out;

import com.jean.gabriel.TransferenciaBancaria.core.domain.Conta;

import java.util.List;

public interface SalvarNovoSaldoContasAdapterOut {

    void salvarNovoSaldoContas(List<Conta> contas);
}
