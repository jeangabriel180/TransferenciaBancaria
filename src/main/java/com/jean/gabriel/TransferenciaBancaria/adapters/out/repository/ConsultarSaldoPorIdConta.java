package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.Conta;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroAoConsultarSaldoException;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroContaNaoEncontradaException;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.ConsultarSaldoPorIdContaAdapterOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsultarSaldoPorIdConta implements ConsultarSaldoPorIdContaAdapterOut {
    private final ContaRepository contaRepository;
    private final static Logger log = LoggerFactory.getLogger(ConsultarSaldoPorIdConta.class);

    public ConsultarSaldoPorIdConta(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public BigDecimal consultarSaldoPorIdConta(String idConta) {
        var tempoRequisicao = System.currentTimeMillis();
        try {
            log.info("Iniciando requisicao de consulta de saldo por id conta: {}", idConta);
            return contaRepository.findById(idConta).map(Conta::getSaldo).orElseThrow(() ->
                    new ErroContaNaoEncontradaException("Conta nao encontrada"));
        } catch (DataAccessException e) {
            log.error("Falha ao consultar saldo por id conta: {}", idConta);
            throw new ErroAoConsultarSaldoException("Erro ao consultar saldo de conta");
        } finally {
            log.info("Encerrada requisicao de consulta de saldo por id conta: {} - {} ms", idConta,
                    System.currentTimeMillis() - tempoRequisicao);
        }
    }
}
