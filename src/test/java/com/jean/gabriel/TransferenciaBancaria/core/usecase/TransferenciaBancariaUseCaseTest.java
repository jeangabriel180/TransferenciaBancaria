package com.jean.gabriel.TransferenciaBancaria.core.usecase;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.request.TransferenciaBancariaRequest;
import com.jean.gabriel.TransferenciaBancaria.adapters.in.response.TransferenciaBancariaResponse;
import com.jean.gabriel.TransferenciaBancaria.core.domain.Conta;
import com.jean.gabriel.TransferenciaBancaria.core.domain.Transferencia;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.*;
import com.jean.gabriel.TransferenciaBancaria.core.usecase.exception.ErroTransferenciaBancariaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.jean.gabriel.TransferenciaBancaria.factory.ContaFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferenciaBancariaUseCaseTest {

    @Mock
    private ConsultarContaRemetentePorIdAdapterOut consultarContaRemetentePorIdAdapterOut;

    @Mock
    private ConsultarContaDestinatarioPorIdAdapterOut consultarContaDestinatarioPorIdAdapterOut;

    @Mock
    private ConsultarTotalTransferenciaDiaPorContaAdapterOut consultarTotalTransferenciaDiaPorContaAdapterOut;

    @Mock
    private BuscarNomeClienteAdapterOut buscarNomeClienteAdapterOut;

    @Mock
    private NotificarBacenAdapterOut notificarBacenAdapterOut;

    @Mock
    private SalvarTransferenciaAdapterOut salvarTransferenciaAdapterOut;

    @Mock
    private SalvarNovoSaldoContasAdapterOut salvarNovoSaldoContasAdapterOut;

    @Captor
    private ArgumentCaptor<List<Conta>> captorContas;

    @Captor
    private ArgumentCaptor<Transferencia> captorTransferencia;


    @InjectMocks
    private TransfenciaBancariaUseCase transfenciaBancariaUseCase;

    @Test
    void deveRealizarTransferenciaComSucesso() {
        TransferenciaBancariaRequest request = new TransferenciaBancariaRequest("03601205409", new BigDecimal("100.0"));
        Conta contaRemetente = gerarContaRemetente();
        Conta contaDestinatario = gerarContaDestinatario();
        BigDecimal totalTransferenciaDia = new BigDecimal("500.0");

        when(consultarContaRemetentePorIdAdapterOut.consultarContaPorId("69853214659")).thenReturn(contaRemetente);
        when(consultarContaDestinatarioPorIdAdapterOut.consultarContaPorId(request.getIdContaDestinatario())).thenReturn(contaDestinatario);
        when(consultarTotalTransferenciaDiaPorContaAdapterOut.consultarTotalTransferenciaDia(contaRemetente.getNumeroConta())).thenReturn(totalTransferenciaDia);
        when(buscarNomeClienteAdapterOut.buscarNomeCliente(contaRemetente.getIdCLiente())).thenReturn("João Neto Filho");
        when(notificarBacenAdapterOut.notificarBacen(contaRemetente.getNumeroConta(), request)).thenReturn(UUID.fromString("0b9cd7d6-173b-469b-9764-51abc64e1ebf"));

        TransferenciaBancariaResponse response = transfenciaBancariaUseCase.executar(contaRemetente.getNumeroConta(), request);

        assertNotNull(response);
        assertEquals("João Neto Filho", response.nomeCliente());
        assertEquals("69853214659", response.numeroContaRemetente());
        assertEquals("03601205409", response.numeroContaDestinatario());
        assertEquals(new BigDecimal("100.0"), response.valorTransferido());
        assertEquals(UUID.fromString("0b9cd7d6-173b-469b-9764-51abc64e1ebf"), response.codigoBacenTransferencia());
        verify(salvarNovoSaldoContasAdapterOut, times(1)).salvarNovoSaldoContas(captorContas.capture());
        verify(salvarTransferenciaAdapterOut, times(1)).salvar(captorTransferencia.capture());

        assertEquals(contaRemetente.getNumeroConta(), captorContas.getValue().get(0).getNumeroConta());
        assertEquals(contaDestinatario.getNumeroConta(), captorContas.getValue().get(1).getNumeroConta());
        assertEquals(contaRemetente.getNumeroConta(), captorTransferencia.getValue().getNumeroContaRemetente());
    }

    @Test
    void deveLancarExcecaoAoVerificarContaInativa() {
        String idConta = "69874532145";
        TransferenciaBancariaRequest request = new TransferenciaBancariaRequest("69874532145", new BigDecimal("100.0"));
        Conta contaRemetente = gerarContaRemetenteInativa();

        when(consultarContaRemetentePorIdAdapterOut.consultarContaPorId(idConta)).thenReturn(contaRemetente);

        assertThrows(ErroTransferenciaBancariaException.class,
                () -> transfenciaBancariaUseCase.executar(idConta, request));
    }

    @Test
    void deveLancarExcecaoAoVerificarLimiteTransferenciaAtingido() {
        TransferenciaBancariaRequest request = new TransferenciaBancariaRequest("03601205409", new BigDecimal("100.0"));
        Conta contaRemetente = gerarContaRemetente();
        BigDecimal totalTransferenciaDia = new BigDecimal("20000.0");

        when(consultarContaRemetentePorIdAdapterOut.consultarContaPorId("69853214659")).thenReturn(contaRemetente);
        when(consultarTotalTransferenciaDiaPorContaAdapterOut.consultarTotalTransferenciaDia(contaRemetente.getNumeroConta())).thenReturn(totalTransferenciaDia);

        assertThrows(ErroTransferenciaBancariaException.class, () ->
                transfenciaBancariaUseCase.executar(contaRemetente.getNumeroConta(), request));
    }

    @Test
    void deveLancarExcecaoChamadaApiExterna() {
        TransferenciaBancariaRequest request = new TransferenciaBancariaRequest("03601205409", new BigDecimal("100.0"));
        Conta contaRemetente = gerarContaRemetente();
        BigDecimal totalTransferenciaDia = new BigDecimal("500.0");

        when(consultarContaRemetentePorIdAdapterOut.consultarContaPorId("69853214659")).thenReturn(contaRemetente);
        when(consultarTotalTransferenciaDiaPorContaAdapterOut.consultarTotalTransferenciaDia(contaRemetente.getNumeroConta())).thenReturn(totalTransferenciaDia);
        when(buscarNomeClienteAdapterOut.buscarNomeCliente(contaRemetente.getIdCLiente())).thenThrow(new RuntimeException("mock exception"));

        assertThrows(ErroTransferenciaBancariaException.class, () ->
                transfenciaBancariaUseCase.executar(contaRemetente.getNumeroConta(), request));

    }

    @Test
    void deveLancarExcecaoAoSalvarNoBancoDeDados() {
        TransferenciaBancariaRequest request = new TransferenciaBancariaRequest("03601205409", new BigDecimal("100.0"));
        Conta contaRemetente = gerarContaRemetente();
        Conta contaDestinatario = gerarContaDestinatario();
        BigDecimal totalTransferenciaDia = new BigDecimal("500.0");

        when(consultarContaRemetentePorIdAdapterOut.consultarContaPorId("69853214659")).thenReturn(contaRemetente);
        when(consultarContaDestinatarioPorIdAdapterOut.consultarContaPorId(request.getIdContaDestinatario())).thenReturn(contaDestinatario);
        when(consultarTotalTransferenciaDiaPorContaAdapterOut.consultarTotalTransferenciaDia(contaRemetente.getNumeroConta())).thenReturn(totalTransferenciaDia);
        when(buscarNomeClienteAdapterOut.buscarNomeCliente(contaRemetente.getIdCLiente())).thenReturn("João Neto Filho");
        doThrow(new DataAccessException("") {}).when(salvarTransferenciaAdapterOut).salvar(any());

        assertThrows(ErroTransferenciaBancariaException.class, () ->
                transfenciaBancariaUseCase.executar(contaRemetente.getNumeroConta(), request));

    }
}
