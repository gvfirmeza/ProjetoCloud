package br.edu.ibmec.cartao_credito.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ibmec.cartao_credito.model.Cartao;
import br.edu.ibmec.cartao_credito.model.Usuario;

import jakarta.validation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UsuarioTest {

    private Usuario usuario;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();

        // Inicializando o validador de Bean Validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Inicializando a lista de cartões
        usuario.setCartoes(new ArrayList<>());
    }

    // Teste para verificar o ID do usuário
    @Test
    public void testSetAndGetId() {
        usuario.setId(1);
        assertEquals(1, usuario.getId());
    }

    // Teste para verificar o nome do usuário
    @Test
    public void testSetAndGetNome() {
        usuario.setNome("João Silva");
        assertEquals("João Silva", usuario.getNome());
    }

    // Teste para verificar a data de nascimento do usuário
    @Test
    public void testSetAndGetDataNascimento() {
        LocalDateTime dataNascimento = LocalDateTime.of(1990, 1, 1, 0, 0);
        usuario.setDataNascimento(dataNascimento);
        assertEquals(dataNascimento, usuario.getDataNascimento());
    }

    // Teste para verificar o CPF do usuário
    @Test
    public void testSetAndGetCpf() {
        usuario.setCpf("123.456.789-00");
        assertEquals("123.456.789-00", usuario.getCpf());
    }

    // Teste para validar CPF inválido
    @Test
    public void testCpfInvalidValidation() {
        usuario.setNome("João Silva");  // Valor válido para nome
        usuario.setDataNascimento(LocalDateTime.of(1990, 1, 1, 0, 0));  // Valor válido para dataNascimento
        usuario.setCpf("123456789");  // CPF inválido
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);

        assertFalse(violations.isEmpty());  // Deve falhar na validação
        assertEquals("Campo cpf não está em um formato correto", violations.iterator().next().getMessage());
    }

    // Teste para verificar o relacionamento de cartões do usuário
    @Test
    public void testSetAndGetCartoes() {
        List<Cartao> cartoes = new ArrayList<>();
        Cartao cartao1 = new Cartao();
        Cartao cartao2 = new Cartao();
        cartoes.add(cartao1);
        cartoes.add(cartao2);

        usuario.setCartoes(cartoes);
        assertEquals(2, usuario.getCartoes().size());
        assertEquals(cartao1, usuario.getCartoes().get(0));
        assertEquals(cartao2, usuario.getCartoes().get(1));
    }

    // Teste para adicionar um cartão ao usuário
    @Test
    public void testAssociarCartao() {
        Cartao cartao = new Cartao();
        usuario.associarCartao(cartao);

        assertEquals(1, usuario.getCartoes().size());
        assertEquals(cartao, usuario.getCartoes().get(0));
    }
}
