package school.sptech.projetoMima.infrastructure.persistance.ClientePersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projetoMima.infrastructure.persistance.ClientePersistance.Enitity.ClienteEntity;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Integer> {
    boolean existsByEmail(String email);

    boolean existsByCPF(String cpf);
}
