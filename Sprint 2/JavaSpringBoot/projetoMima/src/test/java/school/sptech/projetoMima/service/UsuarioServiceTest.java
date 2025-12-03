package school.sptech.projetoMima.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.projetoMima.dto.usuarioDto.UsuarioCadastroDto;
import school.sptech.projetoMima.dto.usuarioDto.UsuarioMapper;
import school.sptech.projetoMima.entity.Usuario;
import school.sptech.projetoMima.exception.Usuario.UsuarioListaVaziaException;
import school.sptech.projetoMima.exception.Usuario.UsuarioNaoEncontradoException;
import school.sptech.projetoMima.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("findFuncionarioById() deve retornar usuário quando encontrado")
    void findFuncionarioById() {
        int id = 1;

        Usuario usuario = new Usuario();
        usuario.setId(id);
        Optional<Usuario> optionalUsuario = Optional.of(usuario);

        Mockito.when(usuarioRepository.findById(id)).thenReturn(optionalUsuario);

        Usuario resultado = usuarioService.findFuncionarioById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        Mockito.verify(usuarioRepository).findById(id);
    }

    @Test
    @DisplayName("findFuncionarioById() deve lançar exceção quando usuário não for encontrado")
    void findFuncionarioById2() {
        int id = 10;

        Optional<Usuario> optionalUsuario = Optional.empty();

        Mockito.when(usuarioRepository.findById(id)).thenReturn(optionalUsuario);

        UsuarioNaoEncontradoException erro = assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.findFuncionarioById(id);
        });

        assertEquals("Funcionário com o ID " + id + " não encontrado!", erro.getMessage());
        Mockito.verify(usuarioRepository).findById(id);
    }



    @Test
    @DisplayName("atualizarFuncionario() deve atualizar e salvar dados do usuário")
    void atualizarFuncionario() {
        int id = 1;

        UsuarioCadastroDto dto = new UsuarioCadastroDto();
        dto.setNome("Maria");
        dto.setTelefone("999999999");
        dto.setEmail("maria@email.com");
        dto.setCargo("Vendedora");
        dto.setEndereco("Rua Sailent Riu");

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(id);

        Optional<Usuario> optionalUsuario = Optional.of(usuarioExistente);

        Mockito.when(usuarioRepository.findById(id)).thenReturn(optionalUsuario);
        Mockito.when(usuarioRepository.save(usuarioExistente)).thenReturn(usuarioExistente);

        Usuario resultado = usuarioService.atualizarFuncionario(dto, id);

        assertNotNull(resultado);
        assertEquals("Maria", resultado.getNome());
        assertEquals("999999999", resultado.getTelefone());
        assertEquals("maria@email.com", resultado.getEmail());
        assertEquals("Vendedora", resultado.getCargo());
        assertEquals("Rua Sailent Riu", resultado.getEndereco());

        Mockito.verify(usuarioRepository).findById(id);
        Mockito.verify(usuarioRepository).save(usuarioExistente);
    }

    @Test
    @DisplayName("atualizarFuncionário() deve lançar exceção quando usuário não for encontrado")
    void atualizarFuncionario2() {
        int id = 99;

        UsuarioCadastroDto dto = new UsuarioCadastroDto();

        Optional<Usuario> optionalUsuario = Optional.empty();

        Mockito.when(usuarioRepository.findById(id)).thenReturn(optionalUsuario);

        UsuarioNaoEncontradoException erro = assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.atualizarFuncionario(dto, id);
        });

        assertEquals("Funcionário com o ID " + id + " não encontrado!", erro.getMessage());

        Mockito.verify(usuarioRepository).findById(id);
        Mockito.verify(usuarioRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("listarFuncionarios() deve retornar lista com usuários")
    void listarFuncionarios() {
        Usuario usuario = new Usuario();
        List<Usuario> lista = new ArrayList<>();
        lista.add(usuario);

        Mockito.when(usuarioRepository.findAll()).thenReturn(lista);

        List<Usuario> resultado = usuarioService.listarFuncionarios();

        assertFalse(resultado.isEmpty());
        Mockito.verify(usuarioRepository).findAll();
    }

    @Test
    @DisplayName("listarFuncionarios() deve lançar exceção quando lista estiver vazia")
    void listarFuncionarios2() {
        List<Usuario> listaVazia = new ArrayList<>();

        Mockito.when(usuarioRepository.findAll()).thenReturn(listaVazia);

        UsuarioListaVaziaException erro = assertThrows(UsuarioListaVaziaException.class, () -> {
            usuarioService.listarFuncionarios();
        });


        assertEquals("Lista de funcionário está vazia", erro.getMessage());

        Mockito.verify(usuarioRepository).findAll();
    }

    @Test
    @DisplayName("excluir() deve deletar usuário quando encontrado")
    void excluir() {
        int id = 1;

        Mockito.when(usuarioRepository.existsById(id)).thenReturn(true);

        usuarioService.excluir(id);

        Mockito.verify(usuarioRepository).deleteById(id);
    }

    @Test
    @DisplayName("excluir() deve lançar exceção quando usuário não existir")
    void excluir2() {

        int id = 1;

        Mockito.when(usuarioRepository.existsById(id)).thenReturn(false);

        UsuarioNaoEncontradoException error = assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.excluir(id);
        });

        assertEquals("Curso não encontrado", error.getMessage());

        Mockito.verify(usuarioRepository, Mockito.never()).deleteById(id);
    }
}
