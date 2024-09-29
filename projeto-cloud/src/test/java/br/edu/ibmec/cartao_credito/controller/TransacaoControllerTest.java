package br.edu.ibmec.cartao_credito.controller;

import br.edu.ibmec.cartao_credito.controller.TransacaoController;
import br.edu.ibmec.cartao_credito.exception.TransacaoException;
import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Transacao;
import br.edu.ibmec.cartao_credito.service.CartaoService;
import br.edu.ibmec.cartao_credito.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransacaoControllerTest {

    @Mock
    private TransacaoService transacaoService;

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private TransacaoController transacaoController;

    private Cartao cartao;
    private Transacao transacao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inicializando um cartão ativo com limite
        cartao = new Cartao();
        cartao.setId(1);
        cartao.setAtivo(true);
        cartao.setLimite(500.0);

        // Inicializando uma transação
        transacao = new Transacao();
        transacao.setId(1);
        transacao.setValor(100.0);
        transacao.setComerciante("Loja X");
    }

    // Teste para autorizar transação com sucesso
    @Test
    public void testAutorizarTransacao_Sucesso() throws Exception {
        when(cartaoService.buscarCartaoPorId(1)).thenReturn(cartao);
        when(transacaoService.autorizacaoTransacao(cartao, 100.0, "Loja X")).thenReturn(transacao);

        Transacao resultado = transacaoController.autorizarTransacao(1, 100.0, "Loja X");

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(100.0, resultado.getValor());
        assertEquals("Loja X", resultado.getComerciante());
        verify(transacaoService, times(1)).autorizacaoTransacao(cartao, 100.0, "Loja X");
    }

    // Teste para exceção ao autorizar transação com cartão não encontrado
    @Test
    public void testAutorizarTransacao_CartaoNaoEncontrado() throws Exception {
        when(cartaoService.buscarCartaoPorId(1)).thenReturn(null);

        Exception exception = assertThrows(TransacaoException.class, () -> {
            transacaoController.autorizarTransacao(1, 100.0, "Loja X");
        });

        assertEquals("Cartão com ID 1 não encontrado.", exception.getMessage());
        verify(transacaoService, never()).autorizacaoTransacao(any(Cartao.class), anyDouble(), anyString());
    }

    // Teste para exceção ao autorizar transação com cartão inativo
    @Test
    public void testAutorizarTransacao_CartaoInativo() throws Exception {
        cartao.setAtivo(false);
        when(cartaoService.buscarCartaoPorId(1)).thenReturn(cartao);

        Exception exception = assertThrows(TransacaoException.class, () -> {
            transacaoController.autorizarTransacao(1, 100.0, "Loja X");
        });

        assertEquals("Cartão com ID 1 está inativo.", exception.getMessage());
        verify(transacaoService, never()).autorizacaoTransacao(any(Cartao.class), anyDouble(), anyString());
    }

    // Teste para exceção ao autorizar transação com limite insuficiente
    @Test
    public void testAutorizarTransacao_LimiteInsuficiente() throws Exception {
        cartao.setLimite(50.0); // Limite insuficiente
        when(cartaoService.buscarCartaoPorId(1)).thenReturn(cartao);

        Exception exception = assertThrows(TransacaoException.class, () -> {
            transacaoController.autorizarTransacao(1, 100.0, "Loja X");
        });

        assertEquals("Limite insuficiente para realizar a transação de valor 100.0", exception.getMessage());
        verify(transacaoService, never()).autorizacaoTransacao(any(Cartao.class), anyDouble(), anyString());
    }

    // Teste para buscar transação por ID com sucesso
    @Test
    public void testBuscarTransacaoPorId_Sucesso() throws Exception {
        when(transacaoService.buscarTransacaoPorId(1)).thenReturn(transacao);

        Transacao resultado = transacaoController.buscarTransacaoPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(100.0, resultado.getValor());
        assertEquals("Loja X", resultado.getComerciante());
        verify(transacaoService, times(1)).buscarTransacaoPorId(1);
    }

    // Teste para exceção ao buscar transação com ID não encontrado
    @Test
    public void testBuscarTransacaoPorId_TransacaoNaoEncontrada() throws Exception {
        when(transacaoService.buscarTransacaoPorId(1)).thenReturn(null);

        Exception exception = assertThrows(TransacaoException.class, () -> {
            transacaoController.buscarTransacaoPorId(1);
        });

        assertEquals("Transação com ID 1 não encontrada.", exception.getMessage());
        verify(transacaoService, times(1)).buscarTransacaoPorId(1);
    }
}
