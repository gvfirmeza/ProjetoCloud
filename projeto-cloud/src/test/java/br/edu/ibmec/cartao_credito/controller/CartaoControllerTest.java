package br.edu.ibmec.cartao_credito.controller;

import br.edu.ibmec.cartao_credito.controller.CartaoController;
import br.edu.ibmec.cartao_credito.service.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartaoControllerTest {

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private CartaoController cartaoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste para ativar cartão com sucesso
    @Test
    public void testAtivarCartao_Sucesso() throws Exception {
        // Simula o comportamento do serviço
        doNothing().when(cartaoService).ativarCartao(1);

        ResponseEntity<String> response = cartaoController.ativarCartao(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cartão ativado com sucesso", response.getBody());
        verify(cartaoService, times(1)).ativarCartao(1);
    }

    // Teste para falha ao ativar cartão (exceção)
    @Test
    public void testAtivarCartao_Falha() throws Exception {
        // Simula uma exceção no serviço
        doThrow(new Exception("Cartão não encontrado")).when(cartaoService).ativarCartao(1);

        ResponseEntity<String> response = cartaoController.ativarCartao(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cartão não encontrado", response.getBody());
        verify(cartaoService, times(1)).ativarCartao(1);
    }

    // Teste para desativar cartão com sucesso
    @Test
    public void testDesativarCartao_Sucesso() throws Exception {
        // Simula o comportamento do serviço
        doNothing().when(cartaoService).desativarCartao(1);

        ResponseEntity<String> response = cartaoController.desativarCartao(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cartão desativado com sucesso", response.getBody());
        verify(cartaoService, times(1)).desativarCartao(1);
    }

    // Teste para falha ao desativar cartão (exceção)
    @Test
    public void testDesativarCartao_Falha() throws Exception {
        // Simula uma exceção no serviço
        doThrow(new Exception("Cartão não encontrado")).when(cartaoService).desativarCartao(1);

        ResponseEntity<String> response = cartaoController.desativarCartao(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cartão não encontrado", response.getBody());
        verify(cartaoService, times(1)).desativarCartao(1);
    }
}
