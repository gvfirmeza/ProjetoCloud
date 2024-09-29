package br.edu.ibmec.cartao_credito.service;

import java.time.LocalDateTime;
import java.util.Optional;

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
        autorizacaoService.verificarAutorizacao(cartao, valor);
        autorizacaoService.verificarAntifraude(cartao, valor, comerciante);

        Transacao transacao = new Transacao();
        transacao.setComerciante(comerciante);
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setValor(valor);
        transacao.setCartao(cartao);

        repository.save(transacao);
        cartao.setLimite(cartao.getLimite() - valor);
        cartao.getTransacoes().add(transacao);

        cartaoRepository.save(cartao);

        return transacao;
    }


    public Transacao buscarTransacaoPorId(int id) {
        Optional<Transacao> transacao = repository.findById(id);
        if (transacao.isPresent()) {
            return transacao.get();
        } else {
            return null;
        }
    }
}