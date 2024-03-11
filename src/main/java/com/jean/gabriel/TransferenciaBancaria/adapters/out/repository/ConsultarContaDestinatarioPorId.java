package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroContaNaoEncontradaException;
import com.jean.gabriel.TransferenciaBancaria.core.domain.Conta;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.ConsultarContaDestinatarioPorIdAdapterOut;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.ConsultarContaRemetentePorIdAdapterOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import static com.jean.gabriel.TransferenciaBancaria.utils.MensagensLogsEnum.*;

@Component
public class ConsultarContaDestinatarioPorId implements ConsultarContaDestinatarioPorIdAdapterOut {
    private final ContaRepository contaRepository;
    private final static Logger log = LoggerFactory.getLogger(ConsultarContaDestinatarioPorId.class);

    public ConsultarContaDestinatarioPorId(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public Conta consultarContaPorId(String idConta) {
        //Esse método pode ser personalizado conforme a necessidade da onde buscar a conta destinataria
        var tempoRequisicao = System.currentTimeMillis();
        try {
            log.info(INICIO_CONSULTA_CONTA.getMsg(), idConta);
            return contaRepository.findById(idConta).orElseThrow(() ->
                    new ErroContaNaoEncontradaException("Conta destinatario nao encontrada")).toDomain();
        } catch (DataAccessException e) {
            log.error(ERRO_CONSULTA_CONTA.getMsg(), idConta);
            throw new ErroContaNaoEncontradaException(e.getMessage());
        } finally {
            log.info(FIM_CONSULTA_CONTA.getMsg(), idConta,
                    System.currentTimeMillis() - tempoRequisicao);
        }
    }
}
