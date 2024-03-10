package com.jean.gabriel.TransferenciaBancaria.core.usecase;

import com.jean.gabriel.TransferenciaBancaria.core.ports.in.ConsultarSaldoPorIdContaAdapterIn;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.ConsultarSaldoPorIdContaAdapterOut;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsultarSaldoPorIdContaUseCase implements ConsultarSaldoPorIdContaAdapterIn {

    private final ConsultarSaldoPorIdContaAdapterOut consultarSaldoPorIdContaAdapterOut;

    public ConsultarSaldoPorIdContaUseCase(ConsultarSaldoPorIdContaAdapterOut consultarSaldoPorIdContaAdapterOut) {
        this.consultarSaldoPorIdContaAdapterOut = consultarSaldoPorIdContaAdapterOut;
    }

    public BigDecimal executar(String idConta) {
        return consultarSaldoPorIdContaAdapterOut.consultarSaldoPorIdConta(idConta);
    }
}
