package br.edu.ibmec.cartao_credito.repository;

import br.edu.ibmec.cartao_credito.model.Cartao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartaoRepositoryTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarCartao() {
        // Cria um cartão simulado
        Cartao cartao = new Cartao();
        cartao.setId(1);  // ID simulado
        cartao.setNumero("1234-5678-9876-5432");
        cartao.setAtivo(true);
        cartao.setLimite(5000.00);

        // Simula o comportamento do repository
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartao);

        // Salva o cartão (sem interagir com um banco real)
        Cartao cartaoSalvo = cartaoRepository.save(cartao);

        // Verifica se o cartão foi salvo com os dados corretos
        assertNotNull(cartaoSalvo);
        assertEquals(1, cartaoSalvo.getId());
        assertEquals("1234-5678-9876-5432", cartaoSalvo.getNumero());
        assertTrue(cartaoSalvo.getAtivo());
        assertEquals(5000.00, cartaoSalvo.getLimite());

        // Verifica se o método save foi chamado
        verify(cartaoRepository, times(1)).save(cartao);
    }

    @Test
    void testBuscarCartaoPorId() {
        // Cria um cartão simulado
        Cartao cartao = new Cartao();
        cartao.setId(1);
        cartao.setNumero("9876-5432-1234-5678");
        cartao.setAtivo(false);
        cartao.setLimite(1000.00);

        // Simula o comportamento do repository
        when(cartaoRepository.findById(1)).thenReturn(Optional.of(cartao));

        // Busca o cartão pelo ID (sem banco real)
        Optional<Cartao> encontrado = cartaoRepository.findById(1);

        // Verifica se o cartão foi encontrado e tem os dados corretos
        assertTrue(encontrado.isPresent());
        assertEquals("9876-5432-1234-5678", encontrado.get().getNumero());

        // Verifica se o método findById foi chamado
        verify(cartaoRepository, times(1)).findById(1);
    }

    @Test
    void testDeletarCartao() {
        // Simula a deleção de um cartão
        doNothing().when(cartaoRepository).deleteById(1);

        // Deleta o cartão
        cartaoRepository.deleteById(1);

        // Verifica se o método deleteById foi chamado
        verify(cartaoRepository, times(1)).deleteById(1);
    }
}
