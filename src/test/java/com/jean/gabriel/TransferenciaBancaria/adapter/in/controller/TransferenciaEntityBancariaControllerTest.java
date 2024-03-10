package com.jean.gabriel.TransferenciaBancaria.adapter.in.controller;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.controller.TransferenciaBancariaController;
import com.jean.gabriel.TransferenciaBancaria.core.ports.in.ConsultarSaldoPorIdContaAdapterIn;
import com.jean.gabriel.TransferenciaBancaria.core.ports.in.TransferenciaBancariaAdapterIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransferenciaEntityBancariaControllerTest {

    @Mock
    ConsultarSaldoPorIdContaAdapterIn consultarSaldoPorIdContaAdapterIn;

    @Mock
    TransferenciaBancariaAdapterIn transferenciaBancariaAdapterIn;


    TransferenciaBancariaController transferenciaBancariaController;

    @BeforeEach
    public void setUp() {
        transferenciaBancariaController = new TransferenciaBancariaController(consultarSaldoPorIdContaAdapterIn,
                transferenciaBancariaAdapterIn);
        ;
    }

    @Test
    void testGetSaldoByIdConta() {
        String idConta = "123";
        BigDecimal saldoEsperado = new BigDecimal("1000.00");
        Mockito.when(consultarSaldoPorIdContaAdapterIn.executar(idConta)).thenReturn(saldoEsperado);
        ResponseEntity<BigDecimal> responseEntity = transferenciaBancariaController.getSaldoByIdConta(idConta);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(saldoEsperado, responseEntity.getBody());
    }
}
