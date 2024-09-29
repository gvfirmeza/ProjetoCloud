package br.edu.ibmec.cartao_credito.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import br.edu.ibmec.cartao_credito.exception.UsuarioException;
import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Usuario;
import br.edu.ibmec.cartao_credito.repository.CartaoRepository;
import br.edu.ibmec.cartao_credito.repository.UsuarioRepository;
import br.edu.ibmec.cartao_credito.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Cartao cartao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializando o objeto Usuario com a lista de cartões vazia
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setCpf("12345678901");
        usuario.setCartoes(new ArrayList<>());  // Inicializa a lista de cartões

        // Inicializando o objeto Cartao
        cartao = new Cartao();
        cartao.setId(1);
        cartao.setAtivo(true);
    }

    // Teste para criar usuário com sucesso
    @Test
    public void testCriarUsuario_Sucesso() throws UsuarioException {
        // Simulando que o usuário com o CPF informado ainda não está cadastrado
        when(usuarioRepository.findUsuarioByCpf("12345678901")).thenReturn(Optional.empty());

        // Simulando o salvamento do usuário
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.criarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("12345678901", resultado.getCpf());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));  // Verifica que o usuário foi salvo
    }

    // Teste para exceção ao tentar criar usuário com CPF já cadastrado
    @Test
    public void testCriarUsuario_CpfJaCadastrado() {
        // Simulando que o usuário com o CPF já está cadastrado
        when(usuarioRepository.findUsuarioByCpf("12345678901")).thenReturn(Optional.of(usuario));

        // Verifica que uma exceção é lançada ao tentar criar um usuário com CPF duplicado
        UsuarioException exception = assertThrows(UsuarioException.class, () -> {
            usuarioService.criarUsuario(usuario);
        });

        assertEquals("Usuario com cpf informado já cadastrado", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));  // O usuário não deve ser salvo
    }

    // Teste para buscar usuário por ID com sucesso
    @Test
    public void testBuscarUsuario_Sucesso() {
        // Simulando que o usuário foi encontrado
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscaUsuario(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(usuarioRepository, times(1)).findById(1);  // Verifica que o método de busca foi chamado
    }

    // Teste para buscar usuário por ID inexistente
    @Test
    public void testBuscarUsuario_UsuarioNaoEncontrado() {
        // Simulando que o usuário não foi encontrado
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.buscaUsuario(1);

        assertNull(resultado);  // Verifica que o resultado é nulo
        verify(usuarioRepository, times(1)).findById(1);  // Verifica que o método de busca foi chamado
    }

    // Teste para associar cartão com sucesso
    @Test
    public void testAssociarCartao_Sucesso() throws Exception {
        // Simulando que o usuário foi encontrado
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        // Simulando o salvamento do cartão e do usuário
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartao);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioService.associarCartao(cartao, 1);

        verify(cartaoRepository, times(1)).save(cartao);  // Verifica que o cartão foi salvo
        verify(usuarioRepository, times(1)).save(usuario);  // Verifica que o usuário foi salvo
    }

    // Teste para exceção ao tentar associar cartão a um usuário inexistente
    @Test
    public void testAssociarCartao_UsuarioNaoEncontrado() {
        // Simulando que o usuário não foi encontrado
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        // Verifica que uma exceção é lançada ao tentar associar cartão a um usuário inexistente
        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.associarCartao(cartao, 1);
        });

        assertEquals("Não encontrei o usuario", exception.getMessage());
        verify(cartaoRepository, never()).save(any(Cartao.class));  // O cartão não deve ser salvo
        verify(usuarioRepository, never()).save(any(Usuario.class));  // O usuário não deve ser salvo
    }

    // Teste para exceção ao tentar associar um cartão inativo
    @Test
    public void testAssociarCartao_CartaoInativo() {
        // Tornando o cartão inativo
        cartao.setAtivo(false);

        // Simulando que o usuário foi encontrado
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        // Verifica que uma exceção é lançada ao tentar associar um cartão inativo
        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.associarCartao(cartao, 1);
        });

        assertEquals("Não posso associar um cartão inativo ao usuário", exception.getMessage());
        verify(cartaoRepository, never()).save(any(Cartao.class));  // O cartão não deve ser salvo
        verify(usuarioRepository, never()).save(any(Usuario.class));  // O usuário não deve ser salvo
    }
}
