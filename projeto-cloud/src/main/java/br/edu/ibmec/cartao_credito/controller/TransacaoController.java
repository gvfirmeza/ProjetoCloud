package br.edu.ibmec.cartao_credito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.cartao_credito.exception.TransacaoException;
import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Transacao;
import br.edu.ibmec.cartao_credito.service.TransacaoService;
import br.edu.ibmec.cartao_credito.service.CartaoService;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private CartaoService cartaoService;

    @PostMapping("/autorizar")
    public Transacao autorizarTransacao(
            @RequestParam int cartaoId,
            @RequestParam double valor,
            @RequestParam String comerciante) throws Exception {
        Cartao cartao = cartaoService.buscarCartaoPorId(cartaoId);
        if (cartao == null) {
            throw new TransacaoException("Cartão com ID " + cartaoId + " não encontrado.");
        }

        if (!cartao.getAtivo()) {
            throw new TransacaoException("Cartão com ID " + cartaoId + " está inativo.");
        }

        if (cartao.getLimite() < valor) {
            throw new TransacaoException("Limite insuficiente para realizar a transação de valor " + valor);
        }

        return transacaoService.autorizacaoTransacao(cartao, valor, comerciante);
    }

    @GetMapping("/{id}")
    public Transacao buscarTransacaoPorId(@PathVariable int id) throws TransacaoException {
        Transacao transacao = transacaoService.buscarTransacaoPorId(id);
        if (transacao != null) {
            return transacao;
        } else {
            throw new TransacaoException("Transação com ID " + id + " não encontrada.");
        }
    }
}
