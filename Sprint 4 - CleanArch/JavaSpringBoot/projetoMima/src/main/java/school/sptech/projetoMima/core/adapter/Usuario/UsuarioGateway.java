package school.sptech.projetoMima.core.adapter.Usuario;

import school.sptech.projetoMima.core.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioGateway {
    Optional<Usuario> findById(Integer id);
    List<Usuario> findAll();
    Usuario save(Usuario usuario);
    void deleteById(Integer id);

    boolean existsById(Integer id);
    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByRecoveryToken(String token);
}
