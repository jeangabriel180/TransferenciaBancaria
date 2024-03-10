package com.jean.gabriel.TransferenciaBancaria.core.ports.out;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.request.TransferenciaBancariaRequest;

import java.util.UUID;

public interface NotificarBacenAdapterOut {
    UUID notificarBacen(String idConta, TransferenciaBancariaRequest request);
}
