package school.sptech.projetoMima.infrastructure.persistance.FornecedorPersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.projetoMima.core.domain.Fornecedor;

import java.util.List;
import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Integer> {
    boolean existsByNome(String nome);
}
