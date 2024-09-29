package br.edu.ibmec.cartao_credito.controller;

import br.edu.ibmec.cartao_credito.controller.UsuarioController;
import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Usuario;
import br.edu.ibmec.cartao_credito.service.UsuarioService;
import br.edu.ibmec.cartao_credito.exception.UsuarioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;
    private Cartao cartao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializando o objeto Usuario
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("João Silva");
        usuario.setCpf("123.456.789-00");

        // Inicializando o objeto Cartao
        cartao = new Cartao();
        cartao.setId(1);
        cartao.setAtivo(true);
    }

    // Teste para criar usuário com sucesso
    @Test
    public void testCriarUsuario_Sucesso() throws UsuarioException {
        when(usuarioService.criarUsuario(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.criarUsuario(usuario);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        verify(usuarioService, times(1)).criarUsuario(any(Usuario.class));
    }

    // Teste para falha ao criar usuário com exceção
    @Test
    public void testCriarUsuario_Falha() throws UsuarioException {
        when(usuarioService.criarUsuario(any(Usuario.class))).thenThrow(new UsuarioException("Usuário já cadastrado"));

        ResponseEntity<Usuario> response = usuarioController.criarUsuario(usuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(usuarioService, times(1)).criarUsuario(any(Usuario.class));
    }

    // Teste para buscar usuário por ID com sucesso
    @Test
    public void testBuscarUsuario_Sucesso() {
        when(usuarioService.buscaUsuario(1)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.buscarUsuario(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        verify(usuarioService, times(1)).buscaUsuario(1);
    }

    // Teste para falha ao buscar usuário (não encontrado)
    @Test
    public void testBuscarUsuario_NaoEncontrado() {
        when(usuarioService.buscaUsuario(1)).thenReturn(null);

        ResponseEntity<Usuario> response = usuarioController.buscarUsuario(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(usuarioService, times(1)).buscaUsuario(1);
    }

    // Teste para associar cartão ao usuário com sucesso
    @Test
    public void testAssociarCartao_Sucesso() throws Exception {
        doNothing().when(usuarioService).associarCartao(any(Cartao.class), eq(1));

        ResponseEntity<String> response = usuarioController.associarCartao(1, cartao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cartão associado com sucesso", response.getBody());
        verify(usuarioService, times(1)).associarCartao(any(Cartao.class), eq(1));
    }

    // Teste para falha ao associar cartão (exceção)
    @Test
    public void testAssociarCartao_Falha() throws Exception {
        doThrow(new Exception("Cartão inativo")).when(usuarioService).associarCartao(any(Cartao.class), eq(1));

        ResponseEntity<String> response = usuarioController.associarCartao(1, cartao);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cartão inativo", response.getBody());
        verify(usuarioService, times(1)).associarCartao(any(Cartao.class), eq(1));
    }
}
