package br.edu.ibmec.cartao_credito.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.edu.ibmec.cartao_credito.exception.TransacaoException;
import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Transacao;
import br.edu.ibmec.cartao_credito.repository.TransacaoRepository;
import br.edu.ibmec.cartao_credito.service.AutorizacaoService;

public class AutorizacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private AutorizacaoService autorizacaoService;

    private Cartao cartao;
    private Transacao transacao1;
    private Transacao transacao2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cartao = new Cartao();
        cartao.setAtivo(true);
        cartao.setLimite(1000);

        transacao1 = new Transacao();
        transacao1.setCartao(cartao);
        transacao1.setValor(100);
        transacao1.setComerciante("Loja A");
        transacao1.setDataTransacao(LocalDateTime.now().minusMinutes(1));

        transacao2 = new Transacao();
        transacao2.setCartao(cartao);
        transacao2.setValor(100);
        transacao2.setComerciante("Loja A");
        transacao2.setDataTransacao(LocalDateTime.now());
    }

    @Test
    public void testVerificarAntifraude_AltaFrequencia() {
        when(transacaoRepository.findByCartaoAndDataTransacaoAfter(eq(cartao), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(transacao1, transacao2, transacao1));

        assertThrows(TransacaoException.class, () -> autorizacaoService.verificarAntifraude(cartao, 100, "Loja A"),
                "Alta-frequência-pequeno-intervalo");
    }

    @Test
    public void testVerificarAntifraude_TransacaoDuplicada() {
        when(transacaoRepository.findByCartaoAndDataTransacaoAfter(eq(cartao), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(transacao1, transacao2));

        assertThrows(TransacaoException.class, () -> autorizacaoService.verificarAntifraude(cartao, 100, "Loja A"),
                "Transação duplicada");
    }

    @Test
    public void testVerificarAntifraude_TransacaoValida() throws TransacaoException {
        when(transacaoRepository.findByCartaoAndDataTransacaoAfter(eq(cartao), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        autorizacaoService.verificarAntifraude(cartao, 100, "Loja A");
        // Nenhuma exceção deve ser lançada
    }
}
