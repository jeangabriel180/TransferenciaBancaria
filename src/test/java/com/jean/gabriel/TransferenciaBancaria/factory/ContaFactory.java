package com.jean.gabriel.TransferenciaBancaria.factory;

import com.jean.gabriel.TransferenciaBancaria.core.domain.Conta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ContaFactory {

    public static Conta gerarContaDestinatario() {
        return new Conta("03601205409", UUID.fromString("cb5f0a82-7a6e-47cd-936b-38905181fe3b"),
                new BigDecimal("200.0"), true, LocalDate.now());
    }

    public static Conta gerarContaRemetente() {
        return new Conta("69853214659", UUID.fromString("cb5f0a82-7a6e-47cd-936b-38905181fe3b"),
                new BigDecimal("500.0"), true, LocalDate.now());
    }

    public static Conta gerarContaRemetenteInativa() {
        return new Conta("69853214659", UUID.fromString("cb5f0a82-7a6e-47cd-936b-38905181fe3b"),
                new BigDecimal("500.0"), false, LocalDate.now());
    }
}
