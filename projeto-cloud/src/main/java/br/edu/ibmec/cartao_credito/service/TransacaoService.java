package br.edu.ibmec.cartao_credito.service;

import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Transacao;
import br.edu.ibmec.cartao_credito.repository.TransacaoRepository;
import br.edu.ibmec.cartao_credito.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public Transacao autorizacaoTransacao(Cartao cartao, double valor, String comerciante) throws Exception {

        // Cartão está ativo.
        if(cartao.getAtivo() == false) {
            throw new Exception("Cartão não está ativo");
        }

        // Cartão tem limite para compra.
        if(cartao.getLimite() < valor) {
            throw new Exception("Cartão tem limite para efetuar compra");
        }

        // Verificar regras
        this.autorizacaoTransacao(cartao, valor, comerciante);

        // Passou nas regras, cria uma nova transação
        Transacao transacao = new Transacao();
        transacao.setComerciante(comerciante);
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setValor(valor);

        // Salva na base de dados
        repository.save(transacao);

        // Diminui o limite do cartão
        cartao.setLimite(cartao.getLimite() - valor);

        // Associa a transação ao cartão e atualiza o limite
        cartao.getTransacoes().add(transacao);

        // Atualiza a base de dados com a nova transação para o cartão e atualiza o limite
        cartaoRepository.save(cartao);

        return transacao;
    }

    private void verificarAntifraude(Cartao cartao, double valor, String comerciante) {

        // Valida se o cartão tem transações nos últimos 3 minutos.
        List<Transacao> ultimasTransacoes = cartao.getTransacoes().stream()
                                            .filter(x -> x.getDataTransacao().isAfter(LocalDateTime.now()))
                                            .toList();

//        List<Transacao> ultimasTransacoes = new ArrayList<>();
//
//        for(Transacao transacao : cartao.getTransacoes()) {
//            if(transacao.getDataTransacao().isAfter(localDateTime)) {
//                ultimasTransacoes.add(transacao);
//            }
//        }

    }

}
