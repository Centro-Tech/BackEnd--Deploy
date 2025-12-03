package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Material;

import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Material.Entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialJpaRepository extends JpaRepository<MaterialEntity, Integer> {
    boolean existsByNome(String nome);
}
