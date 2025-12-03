package school.sptech.projetoMima.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.projetoMima.config.GerenciadorTokenJwt;
import school.sptech.projetoMima.dto.usuarioDto.*;
import school.sptech.projetoMima.entity.Usuario;
import school.sptech.projetoMima.exception.Usuario.UsuarioListaVaziaException;
import school.sptech.projetoMima.exception.Usuario.UsuarioNaoEncontradoException;
import school.sptech.projetoMima.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Usuario findFuncionarioById(int id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Funcionário com o ID " + id + " não encontrado!"));
    }

    public Usuario cadastrarFuncionario(UsuarioCadastroDto dto) {
        Usuario usuarioCad = UsuarioMapper.toEntity(dto);
        String senhaPadrao = gerarSenhaPadrao();
        usuarioCad.setSenha(passwordEncoder.encode(senhaPadrao));
        return usuarioRepository.save(usuarioCad);
    }

    public Usuario atualizarFuncionario(UsuarioCadastroDto dto, Integer id) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Funcionário com o ID " + id + " não encontrado!"));

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setTelefone(dto.getTelefone());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setCargo(dto.getCargo());

        return usuarioRepository.save(usuarioExistente);
    }

    public List<Usuario> listarFuncionarios() {
        List<Usuario> usuarios = new ArrayList<>(usuarioRepository.findAll());
        if (usuarios.isEmpty()) {
            throw new UsuarioListaVaziaException("Lista de funcionários está vazia");
        }
        return usuarios;
    }

    public void excluir(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNaoEncontradoException("Funcionário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioTokenDto autenticar(Usuario usuario) {
        final UsernamePasswordAuthenticationToken credentials =
                new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha());

        final Authentication authentication = authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado = usuarioRepository.findByEmail(usuario.getEmail())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Email do usuário não cadastrado"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public List<UsuarioListarDto> listarTodos() {
        List<Usuario> usuariosEncontrados = usuarioRepository.findAll();
        return usuariosEncontrados.stream().map(UsuarioMapper::of).toList();
    }

    public void criar(Usuario novoUsuario) {
        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        if (novoUsuario.getEndereco() == null) {
            novoUsuario.setEndereco("");
        }

        usuarioRepository.save(novoUsuario);
    }


    public void trocarSenha(TrocarSenhaDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new UsuarioNaoEncontradoException("Usuário não autenticado.");
        }

        String email = auth.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o e-mail: " + email));

        if (!passwordEncoder.matches(dto.getSenhaAtual(), usuario.getSenha())) {
            throw new RuntimeException("Senha atual incorreta.");
        }

        usuario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        usuarioRepository.save(usuario);
    }

    private String gerarSenhaPadrao() {
        return "Mima@123";
    }
}
