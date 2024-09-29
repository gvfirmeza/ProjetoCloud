package br.edu.ibmec.cartao_credito.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.repository.CartaoRepository;
import br.edu.ibmec.cartao_credito.service.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class CartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoService cartaoService;

    private Cartao cartao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cartao = new Cartao();
        cartao.setId(1);
        cartao.setAtivo(false);  // O cartão começa como inativo
    }

    // Teste para ativar cartão
    @Test
    public void testAtivarCartao_Sucesso() throws Exception {
        when(cartaoRepository.findById(1)).thenReturn(Optional.of(cartao));

        cartaoService.ativarCartao(1);

        assertTrue(cartao.getAtivo());  // Verifica se o cartão foi ativado
        verify(cartaoRepository, times(1)).save(cartao);  // Verifica se o cartão foi salvo
    }

    // Teste para ativar cartão que não existe
    @Test
    public void testAtivarCartao_CartaoNaoEncontrado() {
        when(cartaoRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            cartaoService.ativarCartao(1);
        });

        assertEquals("Cartão não encontrado", exception.getMessage());
        verify(cartaoRepository, never()).save(any(Cartao.class));  // Não deve salvar nada
    }

    // Teste para desativar cartão
    @Test
    public void testDesativarCartao_Sucesso() throws Exception {
        cartao.setAtivo(true);  // O cartão começa ativo
        when(cartaoRepository.findById(1)).thenReturn(Optional.of(cartao));

        cartaoService.desativarCartao(1);

        assertFalse(cartao.getAtivo());  // Verifica se o cartão foi desativado
        verify(cartaoRepository, times(1)).save(cartao);  // Verifica se o cartão foi salvo
    }

    // Teste para desativar cartão que não existe
    @Test
    public void testDesativarCartao_CartaoNaoEncontrado() {
        when(cartaoRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            cartaoService.desativarCartao(1);
        });

        assertEquals("Cartão não encontrado", exception.getMessage());
        verify(cartaoRepository, never()).save(any(Cartao.class));  // Não deve salvar nada
    }

    // Teste para buscar cartão por ID que existe
    @Test
    public void testBuscarCartaoPorId_CartaoExistente() {
        when(cartaoRepository.findById(1)).thenReturn(Optional.of(cartao));

        Cartao resultado = cartaoService.buscarCartaoPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    // Teste para buscar cartão por ID que não existe
    @Test
    public void testBuscarCartaoPorId_CartaoNaoExistente() {
        when(cartaoRepository.findById(1)).thenReturn(Optional.empty());

        Cartao resultado = cartaoService.buscarCartaoPorId(1);

        assertNull(resultado);
    }
}
