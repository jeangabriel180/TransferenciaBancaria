package com.jean.gabriel.TransferenciaBancaria.adapter.in.controller;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.ConsultarContaDestinatarioPorId;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.ContaRepository;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.ContaEntity;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroContaNaoEncontradaException;
import com.jean.gabriel.TransferenciaBancaria.core.domain.Conta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultarContaDestinatarioPorIdTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ConsultarContaDestinatarioPorId consultarContaDestinatarioPorId;

    @Test
    void deveConsultarContaComSucesso() {
        ContaEntity contaMock = new ContaEntity("54862354123", UUID.randomUUID(), new BigDecimal("50.0"),
                true, LocalDate.now());
        when(contaRepository.findById("54862354123")).thenReturn(Optional.of(contaMock));

        Conta contaRetornada = consultarContaDestinatarioPorId.consultarContaPorId("54862354123");

        assertEquals(contaMock.toDomain().getNumeroConta(), contaRetornada.getNumeroConta());
        assertEquals(contaMock.toDomain().getIdCLiente(), contaRetornada.getIdCLiente());
        assertEquals(contaMock.toDomain().getDataCriacaoConta(), contaRetornada.getDataCriacaoConta());
        assertEquals(contaMock.toDomain().getAtiva(), contaRetornada.getAtiva());
    }

    @Test
    void deveFalharAoConsultarContaInexistente() {
        when(contaRepository.findById("1234567911")).thenReturn(Optional.empty());

        assertThrows(ErroContaNaoEncontradaException.class,
                () -> consultarContaDestinatarioPorId.consultarContaPorId("1234567911"));
    }

    @Test
    void deveLancarExcecaoAoConsultarConta() {
        when(contaRepository.findById("45632059871")).thenThrow(new DataAccessException("Data access error") {});

        assertThrows(ErroContaNaoEncontradaException.class,
                () -> consultarContaDestinatarioPorId.consultarContaPorId("45632059871"));
    }
}
