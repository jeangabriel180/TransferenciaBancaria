package com.jean.gabriel.TransferenciaBancaria.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Conta implements Serializable {
    private String numeroConta;
    private BigDecimal saldo;
    private Boolean ativa;
    private LocalDate dataCriacao;
    private UUID idCLiente;

    public Conta(String numeroConta, BigDecimal saldo, Boolean ativa, LocalDate dataCriacao, UUID idCLiente) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.ativa = ativa;
        this.dataCriacao = dataCriacao;
        this.idCLiente = idCLiente;
    }
}
