package com.jean.gabriel.TransferenciaBancaria.core.usecase;

import com.jean.gabriel.TransferenciaBancaria.core.ports.out.ConsultarSaldoPorIdContaAdapterOut;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ConsultarSaldoPorIdContaUseCaseTestEntity {

    @Test
    void deveTestarMetodoExeutarComSucesso() {
        String idConta = "123";
        BigDecimal saldoEsperado = new BigDecimal("1000.00");
        ConsultarSaldoPorIdContaAdapterOut consultarSaldoPorIdContaAdapterOut = Mockito.mock(ConsultarSaldoPorIdContaAdapterOut.class);
        Mockito.when(consultarSaldoPorIdContaAdapterOut.consultarSaldoPorIdConta(idConta)).thenReturn(saldoEsperado);
        ConsultarSaldoPorIdContaUseCase consultarSaldoPorIdContaUseCase =
                new ConsultarSaldoPorIdContaUseCase(consultarSaldoPorIdContaAdapterOut);
        BigDecimal saldoRetornado = consultarSaldoPorIdContaUseCase.executar(idConta);
        assertEquals(saldoEsperado, saldoRetornado);
    }

}
