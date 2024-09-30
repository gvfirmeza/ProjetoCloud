package br.edu.ibmec.cartao_credito.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Transacao;

@SpringBootTest
public class TransacaoRepositoryTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    private Cartao cartao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cartao = new Cartao();
        cartao.setId(1); // Exemplo de ID para o cartão
    }

    @Test
    public void testFindByCartaoAndDataTransacaoAfter() {
        LocalDateTime dataTransacao = LocalDateTime.now().minusDays(1);

        Transacao transacao1 = new Transacao();
        transacao1.setId(1);
        transacao1.setValor(100.0);
        transacao1.setComerciante("Comerciante A");
        transacao1.setDataTransacao(LocalDateTime.now());
        transacao1.setCartao(cartao);

        Transacao transacao2 = new Transacao();
        transacao2.setId(2);
        transacao2.setValor(200.0);
        transacao2.setComerciante("Comerciante B");
        transacao2.setDataTransacao(LocalDateTime.now());
        transacao2.setCartao(cartao);

        when(transacaoRepository.findByCartaoAndDataTransacaoAfter(cartao, dataTransacao))
                .thenReturn(Arrays.asList(transacao1, transacao2));

        List<Transacao> resultado = transacaoRepository.findByCartaoAndDataTransacaoAfter(cartao, dataTransacao);

        assertEquals(2, resultado.size());
        assertEquals(transacao1, resultado.get(0));
        assertEquals(transacao2, resultado.get(1));
    }

    @Test
    public void testFindByCartaoAndValorAndComercianteAndDataTransacaoAfter() {
        double valor = 150.0;
        String comerciante = "Comerciante A";
        LocalDateTime dataTransacao = LocalDateTime.now().minusDays(1);

        Transacao transacao1 = new Transacao();
        transacao1.setId(1);
        transacao1.setValor(valor);
        transacao1.setComerciante(comerciante);
        transacao1.setDataTransacao(LocalDateTime.now());
        transacao1.setCartao(cartao);

        when(transacaoRepository.findByCartaoAndValorAndComercianteAndDataTransacaoAfter(cartao, valor, comerciante, dataTransacao))
                .thenReturn(Arrays.asList(transacao1));

        List<Transacao> resultado = transacaoRepository.findByCartaoAndValorAndComercianteAndDataTransacaoAfter(cartao, valor, comerciante, dataTransacao);

        assertEquals(1, resultado.size());
        assertEquals(transacao1, resultado.get(0));
    }
}
