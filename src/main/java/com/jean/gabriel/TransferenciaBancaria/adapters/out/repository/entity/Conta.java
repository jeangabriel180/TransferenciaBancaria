package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_conta")
public class Conta {

    @Id
    @Column(name = "numeroconta")
    String numeroConta;

    @Column(name = "idcliente")
    UUID idCliente;

    @Column(name = "saldo")
    BigDecimal saldo;

    @Column(name = "ativa")
    Boolean ativa;

    @Column(name = "datacriacaoconta")
    LocalDate DataCriacaoConta;

    public BigDecimal getSaldo() {
        return saldo;
    }
}
