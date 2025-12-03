package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Tamanho;

import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Tamanho.Entity.TamanhoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TamanhoJpaRepository extends JpaRepository<TamanhoEntity, Integer> {
    boolean existsByNome(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
