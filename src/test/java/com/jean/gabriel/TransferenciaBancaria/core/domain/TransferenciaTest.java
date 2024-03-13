package com.jean.gabriel.TransferenciaBancaria.core.domain;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.ContaEntity;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.TransferenciaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransferenciaTest {

    @Test
    void deveRetornarEntityConvertida() {
        Transferencia transferencia = new Transferencia(UUID.fromString("2d92fcd1-4d8f-45ae-819b-45597093b724"),
                new BigDecimal("500.0"),
                "698532015642", "003251054202", LocalDateTime.now());
        TransferenciaEntity transferenciaEntity = transferencia.toEntity();

        ContaEntity contaRemetente = (ContaEntity) ReflectionTestUtils.getField(transferenciaEntity, "numeroContaRemetente");
        ContaEntity contaDestinatario = (ContaEntity) ReflectionTestUtils.getField(transferenciaEntity, "numeroContaDestinataria");
        LocalDateTime tempoAtual = (LocalDateTime) ReflectionTestUtils.getField(transferenciaEntity, "dataHoraTransferencia");

        assertEquals(UUID.fromString("2d92fcd1-4d8f-45ae-819b-45597093b724"), ReflectionTestUtils.getField(transferenciaEntity,
                "codigoTransferencia"));
        assertEquals(new BigDecimal("500.0"), ReflectionTestUtils.getField(transferenciaEntity, "valorTransferencia"));
        assertEquals("698532015642", contaRemetente.getNumeroConta());
        assertEquals("003251054202", contaDestinatario.getNumeroConta());
        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS),
                tempoAtual.truncatedTo(ChronoUnit.HOURS));
    }
}
