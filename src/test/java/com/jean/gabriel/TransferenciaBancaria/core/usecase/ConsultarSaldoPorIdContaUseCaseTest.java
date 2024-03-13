package com.jean.gabriel.TransferenciaBancaria.core.usecase;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroAoConsultarSaldoException;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.ConsultarSaldoPorIdContaAdapterOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultarSaldoPorIdContaUseCaseTest {

    @Mock
    private ConsultarSaldoPorIdContaAdapterOut consultarSaldoPorIdContaAdapterOut;

    @InjectMocks
    private ConsultarSaldoPorIdContaUseCase consultarSaldoPorIdContaUseCase;

    @Test
    void deveRetornarSaldoConsultadoComSucesso() {
        String idConta = "12345";
        BigDecimal saldoEsperado = new BigDecimal("1000.0");
        when(consultarSaldoPorIdContaAdapterOut.consultarSaldoPorIdConta(idConta)).thenReturn(saldoEsperado);
        BigDecimal saldoRetornado = consultarSaldoPorIdContaUseCase.executar(idConta);
        assertNotNull(saldoRetornado);
        assertEquals(saldoEsperado, saldoRetornado);
    }

    @Test
    void deveCOnsultarSaldoZerado() {
        String idConta = "54321";
        when(consultarSaldoPorIdContaAdapterOut.consultarSaldoPorIdConta(idConta)).thenReturn(BigDecimal.ZERO);
        BigDecimal saldoRetornado = consultarSaldoPorIdContaUseCase.executar(idConta);
        assertNotNull(saldoRetornado);
        assertEquals(BigDecimal.ZERO, saldoRetornado);
    }

    @Test
    void deveLancarExcecaoAoConsultarSaldo() {
        String idConta = "54321";
        when(consultarSaldoPorIdContaAdapterOut.consultarSaldoPorIdConta(idConta))
                .thenThrow(new ErroAoConsultarSaldoException("mock error"));

        assertThrows(ErroAoConsultarSaldoException.class, () -> consultarSaldoPorIdContaUseCase.executar(idConta));

    }
}
