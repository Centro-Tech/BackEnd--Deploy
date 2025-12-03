package school.sptech.projetoMima.infrastructure.persistance.UsuarioPersistance;

import org.springframework.stereotype.Component;
import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.domain.Usuario;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioJpaAdapter implements UsuarioGateway {

    private final UsuarioRepository repository;

    public UsuarioJpaAdapter(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        return repository.findById(id).map(UsuarioEntityMapper::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll().stream()
                .map(UsuarioEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity saved = repository.save(UsuarioEntityMapper.toEntity(usuario));
        return UsuarioEntityMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByTelefone(String telefone) {
        return repository.existsByTelefone(telefone);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return repository.findByEmail(email).map(UsuarioEntityMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByRecoveryToken(String token) {
        return repository.findByRecoveryToken(token).map(UsuarioEntityMapper::toDomain);
    }
}
