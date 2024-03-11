package com.jean.gabriel.TransferenciaBancaria.core.usecase;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.request.TransferenciaBancariaRequest;
import com.jean.gabriel.TransferenciaBancaria.adapters.in.response.TransferenciaBancariaResponse;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.ConsultarSaldoPorIdConta;
import com.jean.gabriel.TransferenciaBancaria.core.ports.in.TransferenciaBancariaAdapterIn;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.*;
import com.jean.gabriel.TransferenciaBancaria.core.usecase.exception.ErroTransferenciaBancariaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.jean.gabriel.TransferenciaBancaria.utils.MensagensLogsEnum.*;

@Component
public class TransfenciaBancariaUseCase implements TransferenciaBancariaAdapterIn {

    private final ConsultarContaPorIdAdapterOut consultarContaPorIdAdapterOut;
    private final ConsultarTotalTransferenciaDiaPorContaAdapterOut consultarTotalTransferenciaDiaPorContaAdapterOut;
    private final BuscarNomeClienteAdapterOut buscarNomeClienteAdapterOut;
    private final NotificarBacenAdapterOut notificarBacenAdapterOut;
    //   private final SalvarTransferenciaAdapterOut salvarTransferenciaAdapterOut;

    private final static Logger log = LoggerFactory.getLogger(TransfenciaBancariaUseCase.class);


    public TransfenciaBancariaUseCase(ConsultarContaPorIdAdapterOut consultarContaPorIdAdapterOut,
                                      ConsultarTotalTransferenciaDiaPorContaAdapterOut consultarTotalTransferenciaDiaPorContaAdapterOut,
                                      BuscarNomeClienteAdapterOut buscarNomeClienteAdapterOut, NotificarBacenAdapterOut
                                              notificarBacenAdapterOut) {
        this.consultarContaPorIdAdapterOut = consultarContaPorIdAdapterOut;
        this.consultarTotalTransferenciaDiaPorContaAdapterOut = consultarTotalTransferenciaDiaPorContaAdapterOut;
        this.buscarNomeClienteAdapterOut = buscarNomeClienteAdapterOut;
        this.notificarBacenAdapterOut = notificarBacenAdapterOut;
    }

    @Override
    public TransferenciaBancariaResponse executar(String idConta, TransferenciaBancariaRequest request) {
        try {
            log.info(INICIO_TRANSFERENCIA_BANCARIA.getMsg(), idConta);
            var contaRemetente = consultarContaPorIdAdapterOut.consultarContaPorId(idConta);
            var totalTransferenciaDia = consultarTotalTransferenciaDiaPorContaAdapterOut.consultarTotalTransferenciaDia(idConta);
            var nomeCliente = buscarNomeClienteAdapterOut.buscarNomeCliente(contaRemetente.getIdCLiente());
            var contaDestinatario = consultarContaPorIdAdapterOut.consultarContaPorId(request.getIdContaDestinatario());

            //TODO criar um respository pra salvar a operacao de transferencia e o novo saldo das contas
            if (!contaRemetente.getAtiva()) throw new ErroTransferenciaBancariaException("Conta inativa");
            if (totalTransferenciaDia.compareTo(new BigDecimal("1000.0")) > 0)
                throw new ErroTransferenciaBancariaException("Limite atingido");

            contaDestinatario.deposito(request.getValorTransferencia());
            contaRemetente.retirada(request.getValorTransferencia());
            var codigoBacenTransferencia = notificarBacenAdapterOut.notificarBacen(idConta, request);

            return new TransferenciaBancariaResponse(nomeCliente, contaRemetente.getNumeroConta(),
                    contaDestinatario.getNumeroConta(), request.getValorTransferencia(), codigoBacenTransferencia);
        } catch (Exception e) {
            log.error(ERRO_TRANSFERENCIA_BANCARIA.getMsg(), idConta);
            throw new ErroTransferenciaBancariaException(e.getMessage());
        } finally {
            log.info(FIM_TRANSFERENCIA_BANCARIA.getMsg(), idConta);
        }
    }
}
