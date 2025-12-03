package school.sptech.projetoMima.repository.auxiliares;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projetoMima.entity.item.Cor;

public interface CorRepository extends JpaRepository<Cor, Integer> {
    boolean existsByNomeIgnoreCase(String nome);
}
