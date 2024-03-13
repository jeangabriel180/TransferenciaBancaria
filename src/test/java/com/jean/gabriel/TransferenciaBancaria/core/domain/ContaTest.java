package com.jean.gabriel.TransferenciaBancaria.core.domain;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.ContaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ContaTest {

    @Test
    void deveRetornarEntityConvertida() {
        Conta conta = new Conta("56489453216", UUID.fromString("82a75458-49ee-4e91-99d1-7acb97a40830"),
                new BigDecimal("500.0"), true, LocalDate.now());

        ContaEntity contaEntity = conta.toEntity();

        assertEquals("56489453216", contaEntity.getNumeroConta());
        assertEquals(UUID.fromString("82a75458-49ee-4e91-99d1-7acb97a40830"),
                ReflectionTestUtils.getField(conta, "idCLiente"));
        assertEquals(true, ReflectionTestUtils.getField(conta, "ativa"));
        assertEquals(LocalDate.now(), ReflectionTestUtils.getField(conta, "dataCriacaoConta"));
        assertEquals(new BigDecimal("500.0"), contaEntity.getSaldo());
    }
}
