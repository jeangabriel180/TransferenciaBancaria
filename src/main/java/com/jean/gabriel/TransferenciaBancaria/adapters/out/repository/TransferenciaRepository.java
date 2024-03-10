package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.TransferenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransferenciaRepository extends JpaRepository<TransferenciaEntity, UUID> {
    @Query("SELECT t FROM TransferenciaEntity t WHERE t.numeroContaRemetente.numeroConta = :idConta")
    List<TransferenciaEntity> findByNumeroContaRemetente(@Param("idConta") String idConta);

}
