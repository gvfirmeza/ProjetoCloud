package br.edu.ibmec.cartao_credito.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Transacao;

import java.time.LocalDateTime;

public class TransacaoTest {

    private Transacao transacao;
    private Cartao cartao;

    @BeforeEach
    public void setUp() {
        transacao = new Transacao();
        cartao = new Cartao();
        cartao.setId(1);
    }

    // Teste para verificar o ID da transação
    @Test
    public void testSetAndGetId() {
        transacao.setId(1);
        assertEquals(1, transacao.getId());
    }

    // Teste para verificar a data da transação
    @Test
    public void testSetAndGetDataTransacao() {
        LocalDateTime now = LocalDateTime.now();
        transacao.setDataTransacao(now);
        assertEquals(now, transacao.getDataTransacao());
    }

    // Teste para verificar o valor da transação
    @Test
    public void testSetAndGetValor() {
        transacao.setValor(200.50);
        assertEquals(200.50, transacao.getValor(), 0.001); // Delta usado para comparação de doubles
    }

    // Teste para verificar o comerciante da transação
    @Test
    public void testSetAndGetComerciante() {
        transacao.setComerciante("Loja X");
        assertEquals("Loja X", transacao.getComerciante());
    }

    // Teste para verificar o relacionamento da transação com o cartão
    @Test
    public void testSetAndGetCartao() {
        transacao.setCartao(cartao);
        assertEquals(cartao, transacao.getCartao());
    }
}
