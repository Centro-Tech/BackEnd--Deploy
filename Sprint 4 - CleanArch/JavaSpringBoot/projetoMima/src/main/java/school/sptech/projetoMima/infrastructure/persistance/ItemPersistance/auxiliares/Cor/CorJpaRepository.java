package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Cor;

import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Cor.Entity.CorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorJpaRepository extends JpaRepository<CorEntity, Integer> {
    boolean existsByNome(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
