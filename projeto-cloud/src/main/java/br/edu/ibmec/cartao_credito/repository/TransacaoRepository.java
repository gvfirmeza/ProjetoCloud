package br.edu.ibmec.cartao_credito.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    List<Transacao> findByCartaoAndDataTransacaoAfter(Cartao cartao, LocalDateTime dataTransacao);

    List<Transacao> findByCartaoAndValorAndComercianteAndDataTransacaoAfter(Cartao cartao, double valor, String comerciante, LocalDateTime dataTransacao);
}