package com.jean.gabriel.TransferenciaBancaria.core.ports.out;

import java.util.UUID;

public interface BuscarNomeClienteAdapterOut {

    String buscarNomeCliente(UUID idCliente);

}
