package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Categoria;

import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Categoria.Entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaJpaRepository extends JpaRepository<CategoriaEntity, Integer> {
    boolean existsByNome(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
