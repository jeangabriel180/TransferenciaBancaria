package com.jean.gabriel.TransferenciaBancaria.core.ports.in;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.request.TransferenciaBancariaRequest;
import com.jean.gabriel.TransferenciaBancaria.adapters.in.response.TransferenciaBancariaResponse;

public interface TransferenciaBancariaAdapterIn {

    public TransferenciaBancariaResponse executar(String idConta, TransferenciaBancariaRequest request) throws Exception;
}
