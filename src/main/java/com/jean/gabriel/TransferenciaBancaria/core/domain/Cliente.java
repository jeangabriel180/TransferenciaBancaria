package com.jean.gabriel.TransferenciaBancaria.core.domain;

import java.io.Serializable;
import java.util.UUID;

public class Cliente implements Serializable {
    private UUID idCliente;
    private String nome;


    public Cliente(String nome, UUID idCliente) {
        this.nome = nome;
        this.idCliente = idCliente;
    }
}
