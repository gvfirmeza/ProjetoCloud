package br.edu.ibmec.cartao_credito.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.cartao_credito.exception.UsuarioException;
import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Usuario;
import br.edu.ibmec.cartao_credito.repository.CartaoRepository;
import br.edu.ibmec.cartao_credito.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public Usuario criarUsuario(Usuario usuario) throws UsuarioException {
       
        Optional<Usuario> optUsuario = this.usuarioRepository.findUsuarioByCpf(usuario.getCpf());

        if (optUsuario.isPresent()) {
            throw new UsuarioException("Usuario com cpf informado já cadastrado");
        }

        usuarioRepository.save(usuario);

        return usuario;
    }

    public Usuario buscaUsuario(int id) {
        return this.findUsuario(id);
    }

    public void associarCartao(Cartao cartao, int id) throws Exception {

        Usuario usuario = this.findUsuario(id);

        if (usuario == null) {
            throw new Exception("Não encontrei o usuario");
        }

        if (cartao.getAtivo() == false) {
            throw new Exception("Não posso associar um cartão inativo ao usuário");
        }

        usuario.associarCartao(cartao);

        cartaoRepository.save(cartao);

        usuarioRepository.save(usuario);

    }

    private Usuario findUsuario(int id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty())
            return null;

        return usuario.get();

    }
}
