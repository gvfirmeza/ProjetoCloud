package br.edu.ibmec.cartao_credito.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Transacao;

import java.util.ArrayList;
import java.util.List;

public class CartaoTest {

    private Cartao cartao;

    @BeforeEach
    public void setUp() {
        cartao = new Cartao();
    }

    // Teste para verificar o ID do cartão
    @Test
    public void testSetAndGetId() {
        cartao.setId(1);
        assertEquals(1, cartao.getId());
    }

    // Teste para verificar se o cartão está ativo
    @Test
    public void testSetAndGetAtivo() {
        cartao.setAtivo(true);
        assertTrue(cartao.getAtivo());

        cartao.setAtivo(false);
        assertFalse(cartao.getAtivo());
    }

    // Teste para verificar o limite do cartão
    @Test
    public void testSetAndGetLimite() {
        cartao.setLimite(5000.0);
        assertEquals(5000.0, cartao.getLimite(), 0.001);  // delta usado para comparação de doubles
    }

    // Teste para verificar o número do cartão
    @Test
    public void testSetAndGetNumero() {
        cartao.setNumero("1234-5678-9012-3456");
        assertEquals("1234-5678-9012-3456", cartao.getNumero());
    }

    // Teste para verificar as transações
    @Test
    public void testSetAndGetTransacoes() {
        List<Transacao> transacoes = new ArrayList<>();
        Transacao transacao1 = new Transacao();
        Transacao transacao2 = new Transacao();
        transacoes.add(transacao1);
        transacoes.add(transacao2);

        cartao.setTransacoes(transacoes);
        assertEquals(2, cartao.getTransacoes().size());
        assertEquals(transacao1, cartao.getTransacoes().get(0));
        assertEquals(transacao2, cartao.getTransacoes().get(1));
    }

    // Teste para adicionar uma transação ao cartão
    @Test
    public void testAddTransacao() {
        List<Transacao> transacoes = new ArrayList<>();
        cartao.setTransacoes(transacoes);

        Transacao transacao = new Transacao();
        cartao.getTransacoes().add(transacao);

        assertEquals(1, cartao.getTransacoes().size());
        assertEquals(transacao, cartao.getTransacoes().get(0));
    }
}
