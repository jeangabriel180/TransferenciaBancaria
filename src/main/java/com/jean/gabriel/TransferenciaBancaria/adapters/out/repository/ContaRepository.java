package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, String> {
}
