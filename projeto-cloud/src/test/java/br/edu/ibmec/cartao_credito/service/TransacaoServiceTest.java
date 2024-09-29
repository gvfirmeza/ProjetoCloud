package br.edu.ibmec.cartao_credito.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Transacao;
import br.edu.ibmec.cartao_credito.repository.CartaoRepository;
import br.edu.ibmec.cartao_credito.repository.TransacaoRepository;
import br.edu.ibmec.cartao_credito.service.AutorizacaoService;
import br.edu.ibmec.cartao_credito.service.TransacaoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private CartaoRepository cartaoRepository;

    @Mock
    private AutorizacaoService autorizacaoService;

    @InjectMocks
    private TransacaoService transacaoService;

    private Cartao cartao;
    private Transacao transacao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializando o objeto Cartao
        cartao = new Cartao();
        cartao.setId(1);
        cartao.setLimite(1000.0);
        cartao.setAtivo(true);
        cartao.setTransacoes(new ArrayList<>());  // Inicializando a lista de transações manualmente no teste

        // Inicializando o objeto Transacao
        transacao = new Transacao();
        transacao.setId(1);
        transacao.setValor(100.0);
        transacao.setComerciante("Loja X");
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setCartao(cartao);
    }

    // Teste para autorização de transação com sucesso
    @Test
    public void testAutorizacaoTransacao_Sucesso() throws Exception {
        // Simula a verificação de autorização e antifraude sem exceção
        doNothing().when(autorizacaoService).verificarAutorizacao(cartao, 100.0);
        doNothing().when(autorizacaoService).verificarAntifraude(cartao, 100.0, "Loja X");

        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartao);

        Transacao result = transacaoService.autorizacaoTransacao(cartao, 100.0, "Loja X");

        assertNotNull(result);
        assertEquals(100.0, result.getValor());
        assertEquals("Loja X", result.getComerciante());
        assertEquals(cartao, result.getCartao());

        // Verifica se o limite foi atualizado corretamente
        assertEquals(900.0, cartao.getLimite());

        // Verifica se as operações de salvar transação e cartão foram chamadas
        verify(transacaoRepository, times(1)).save(any(Transacao.class));
        verify(cartaoRepository, times(1)).save(cartao);
    }

    // Teste para exceção ao autorizar transação devido a falha na autorização
    @Test
    public void testAutorizacaoTransacao_FalhaAutorizacao() throws Exception {
        // Simula a exceção ao verificar autorização
        doThrow(new RuntimeException("Cartão não ativo")).when(autorizacaoService).verificarAutorizacao(cartao, 100.0);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            transacaoService.autorizacaoTransacao(cartao, 100.0, "Loja X");
        });

        assertEquals("Cartão não ativo", exception.getMessage());

        // Verifica que nenhuma transação foi salva
        verify(transacaoRepository, never()).save(any(Transacao.class));
        verify(cartaoRepository, never()).save(any(Cartao.class));
    }

    // Teste para exceção ao autorizar transação devido a falha antifraude
    @Test
    public void testAutorizacaoTransacao_FalhaAntifraude() throws Exception {
        // Simula a exceção ao verificar antifraude
        doNothing().when(autorizacaoService).verificarAutorizacao(cartao, 100.0);
        doThrow(new RuntimeException("Transação duplicada")).when(autorizacaoService).verificarAntifraude(cartao, 100.0, "Loja X");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            transacaoService.autorizacaoTransacao(cartao, 100.0, "Loja X");
        });

        assertEquals("Transação duplicada", exception.getMessage());

        // Verifica que nenhuma transação foi salva
        verify(transacaoRepository, never()).save(any(Transacao.class));
        verify(cartaoRepository, never()).save(any(Cartao.class));
    }

    // Teste para buscar transação por ID existente
    @Test
    public void testBuscarTransacaoPorId_TransacaoExistente() {
        when(transacaoRepository.findById(1)).thenReturn(Optional.of(transacao));

        Transacao result = transacaoService.buscarTransacaoPorId(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(100.0, result.getValor());
    }

    // Teste para buscar transação por ID inexistente
    @Test
    public void testBuscarTransacaoPorId_TransacaoNaoExistente() {
        when(transacaoRepository.findById(1)).thenReturn(Optional.empty());

        Transacao result = transacaoService.buscarTransacaoPorId(1);

        assertNull(result);
    }
}
