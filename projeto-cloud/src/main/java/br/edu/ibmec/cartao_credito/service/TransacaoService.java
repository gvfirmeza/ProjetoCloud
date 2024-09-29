package br.edu.ibmec.cartao_credito.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Transacao;
import br.edu.ibmec.cartao_credito.repository.CartaoRepository;
import br.edu.ibmec.cartao_credito.repository.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AutorizacaoService autorizacaoService;

    public Transacao autorizacaoTransacao(Cartao cartao, double valor, String comerciante) throws Exception {
        // Verifica autorização
        autorizacaoService.verificarAutorizacao(cartao, valor);

        // Verifica regras antifraude
        autorizacaoService.verificarAntifraude(cartao, valor, comerciante);

        // Criar nova transação
        Transacao transacao = new Transacao();
        transacao.setComerciante(comerciante);
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setValor(valor);

        // Salvar transação e atualizar limite
        repository.save(transacao);
        cartao.setLimite(cartao.getLimite() - valor);
        cartao.getTransacoes().add(transacao);

        // Atualizar o cartão
        cartaoRepository.save(cartao);

        return transacao;
    }
}
