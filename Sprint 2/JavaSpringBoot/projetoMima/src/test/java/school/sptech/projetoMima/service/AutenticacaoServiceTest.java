package school.sptech.projetoMima.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import school.sptech.projetoMima.entity.Usuario;
import school.sptech.projetoMima.repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve retornar usuário se o e-mail existir")
    void testeQuandoEmailExiste() {
        String email = "usuario@teste.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha("senha123");

        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        UserDetails resultado = autenticacaoService.loadUserByUsername(email);

        assertNotNull(resultado);
        assertEquals(email, resultado.getUsername());
        assertEquals("senha123", resultado.getPassword());

        Mockito.verify(usuarioRepository).findByEmail(email);
    }

    @Test
    @DisplayName("Deve dar erro se o e-mail não existir")
    void testeQuandoEmailNaoExiste() {
        String email = "naoexiste@teste.com";

        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        UsernameNotFoundException erro = assertThrows(UsernameNotFoundException.class, () -> {
            autenticacaoService.loadUserByUsername(email);
        });

        assertEquals("usuario: naoexiste@teste.com nao encontrado", erro.getMessage());
        Mockito.verify(usuarioRepository).findByEmail(email);
    }
}
