package br.edu.ibmec.cartao_credito.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.cartao_credito.exception.TransacaoException;
import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Transacao;
import br.edu.ibmec.cartao_credito.repository.TransacaoRepository;

@Service
public class AutorizacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public void verificarAutorizacao(Cartao cartao, double valor) throws TransacaoException {
        if (!cartao.getAtivo()) {
            throw new TransacaoException("Cartão não ativo");
        }

        if (valor > cartao.getLimite()) {
            throw new TransacaoException("Limite insuficiente");
        }
    }

    public void verificarAntifraude(Cartao cartao, double valor, String comerciante) throws TransacaoException {
        LocalDateTime doisMinutosAtras = LocalDateTime.now().minusMinutes(2);
        List<Transacao> transacoesRecentes = transacaoRepository.findByCartaoAndDataTransacaoAfter(cartao, doisMinutosAtras);

        if (transacoesRecentes.size() >= 3) {
            throw new TransacaoException("Alta-frequência-pequeno-intervalo");
        }

        long transacoesDuplicadas = transacoesRecentes.stream()
                .filter(t -> t.getValor() == valor && t.getComerciante().equals(comerciante))
                .count();

        if (transacoesDuplicadas >= 2) {
            throw new TransacaoException("Transação duplicada");
        }
    }
}