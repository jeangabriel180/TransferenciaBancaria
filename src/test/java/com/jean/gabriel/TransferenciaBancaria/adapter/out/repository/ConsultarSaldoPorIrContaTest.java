package com.jean.gabriel.TransferenciaBancaria.adapter.out.repository;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.ConsultarSaldoPorIdConta;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.ContaRepository;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.Conta;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroAoConsultarSaldoException;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroContaNaoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ConsultarSaldoPorIrContaTest {

    @Mock
    ContaRepository contaRepository;

    ConsultarSaldoPorIdConta consultarSaldoPorIdConta;

    @BeforeEach
    public void setUp() {
        consultarSaldoPorIdConta = new ConsultarSaldoPorIdConta(contaRepository);
    }

    @Test
    void testConsultarSaldoPorIdContaContaSucesso() {
        String idConta = "123";
        BigDecimal saldoEsperado = new BigDecimal("1000.00");
        var mockConta = new Conta();
        ReflectionTestUtils.setField(mockConta, "saldo", new BigDecimal("1000.00"));
        Mockito.when(contaRepository.findById(idConta)).thenReturn(Optional.of(mockConta));
        BigDecimal saldoRetornado = consultarSaldoPorIdConta.consultarSaldoPorIdConta(idConta);
        assertEquals(saldoEsperado, saldoRetornado);
    }

    @Test
    void testConsultarSaldoPorIdContaContaNaoEncontrada() {
        String idConta = "456";
        Mockito.when(contaRepository.findById(idConta)).thenReturn(Optional.empty());
        assertThrows(ErroContaNaoEncontradaException.class, () -> consultarSaldoPorIdConta.consultarSaldoPorIdConta(idConta));
    }

    @Test
    void testConsultarSaldoPorIdContaDataAccessException() {
        String idConta = "789";
        Mockito.when(contaRepository.findById(idConta)).thenThrow(new DataAccessException("Simulated DataAccessException") {});
        assertThrows(ErroAoConsultarSaldoException.class, () -> consultarSaldoPorIdConta.consultarSaldoPorIdConta(idConta));
    }
}
