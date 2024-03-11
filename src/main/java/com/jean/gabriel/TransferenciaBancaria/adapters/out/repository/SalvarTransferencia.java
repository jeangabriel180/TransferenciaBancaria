package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroAoConsultarSaldoException;
import com.jean.gabriel.TransferenciaBancaria.core.domain.Transferencia;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.SalvarTransferenciaAdapterOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import static com.jean.gabriel.TransferenciaBancaria.utils.MensagensLogsEnum.*;

@Component
public class SalvarTransferencia implements SalvarTransferenciaAdapterOut {

    private final TransferenciaRepository transferenciaRepository;
    private final static Logger log = LoggerFactory.getLogger(SalvarTransferenciaAdapterOut.class);


    public SalvarTransferencia(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    @Override
    public void salvar(Transferencia transferencia) {
        var tempoRequisicao = System.currentTimeMillis();
        try {
            log.info(INICIO_SALVAR_TRANSFERENCIA.getMsg(), transferencia.getNumeroContaRemetente());
            transferenciaRepository.save(transferencia.toEntity());
        } catch (DataAccessException e) {
            log.error(ERRO_SALVAR_TRANSFERENCIA.getMsg(), transferencia.getNumeroContaRemetente());
            throw new ErroAoConsultarSaldoException("Erro ao gravar transferencia");
        } finally {
            log.info(FIM_SALVAR_TRANSFERENCIA.getMsg(),
                    transferencia.getNumeroContaRemetente(), System.currentTimeMillis() - tempoRequisicao);
        }
    }
}
