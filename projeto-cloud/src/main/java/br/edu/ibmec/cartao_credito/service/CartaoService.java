package br.edu.ibmec.cartao_credito.service;

import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public void ativarCartao(int id) throws Exception {
        Optional<Cartao> cartao = cartaoRepository.findById(id);
        if (cartao.isEmpty()) {
            throw new Exception("Cartão não encontrado");
        }

        Cartao cartaoAtivar = cartao.get();
        cartaoAtivar.setAtivo(true);
        cartaoRepository.save(cartaoAtivar);
    }

    public void desativarCartao(int id) throws Exception {
        Optional<Cartao> cartao = cartaoRepository.findById(id);
        if (cartao.isEmpty()) {
            throw new Exception("Cartão não encontrado");
        }

        Cartao cartaoDesativar = cartao.get();
        cartaoDesativar.setAtivo(false);
        cartaoRepository.save(cartaoDesativar);
    }

    public Cartao buscarCartaoPorId(int id) {
        return cartaoRepository.findById(id).orElse(null);
    }
}
