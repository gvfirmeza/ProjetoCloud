package br.edu.ibmec.cartao_credito.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ibmec.cartao_credito.model.Usuario;

@SpringBootTest
public class UsuarioRepositoryTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criação de um exemplo de usuário
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("João Silva");
        usuario.setCpf("12345678900");
    }

    @Test
    public void testFindUsuarioByCpf() {
        // Mockando o comportamento do repositório para quando o CPF é encontrado
        when(usuarioRepository.findUsuarioByCpf("12345678900")).thenReturn(Optional.of(usuario));

        // Chamada do método a ser testado
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findUsuarioByCpf("12345678900");

        // Verificações
        assertTrue(usuarioEncontrado.isPresent());
        assertEquals(usuario.getCpf(), usuarioEncontrado.get().getCpf());
        assertEquals(usuario.getNome(), usuarioEncontrado.get().getNome());
    }

    @Test
    public void testFindUsuarioByCpfNotFound() {
        // Mockando o comportamento do repositório para quando o CPF não é encontrado
        when(usuarioRepository.findUsuarioByCpf("00000000000")).thenReturn(Optional.empty());

        // Chamada do método a ser testado
        Optional<Usuario> usuarioNaoEncontrado = usuarioRepository.findUsuarioByCpf("00000000000");

        // Verificações
        assertTrue(usuarioNaoEncontrado.isEmpty());
    }
}
