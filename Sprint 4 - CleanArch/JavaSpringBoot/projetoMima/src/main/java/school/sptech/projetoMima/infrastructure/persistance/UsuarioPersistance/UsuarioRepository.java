package school.sptech.projetoMima.infrastructure.persistance.UsuarioPersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);
    Optional<UsuarioEntity> findByEmail(String email);
    Optional<UsuarioEntity> findByRecoveryToken(String recoveryToken);
}
