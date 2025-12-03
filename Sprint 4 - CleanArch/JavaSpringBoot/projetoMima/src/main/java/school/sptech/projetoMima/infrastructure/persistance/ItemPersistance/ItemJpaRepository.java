package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.Entity.ItemEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemJpaRepository extends JpaRepository<ItemEntity, Integer> {
    boolean existsByCodigo(String codigo);

    List<ItemEntity> findByCategoriaNomeContainsIgnoreCase(String nomeCategoria);

    List<ItemEntity> findByFornecedorNomeContainsIgnoreCase(String nome);

    List<ItemEntity> findByNomeContainsIgnoreCase(String nome);

    Optional<ItemEntity> findByCodigo(String codigo);

    List<ItemEntity> findByCodigoContainsIgnoreCase(String codigo);

    void deleteByCodigo(String codigo);
    
    boolean existsByMaterialId(Integer materialId);
}
