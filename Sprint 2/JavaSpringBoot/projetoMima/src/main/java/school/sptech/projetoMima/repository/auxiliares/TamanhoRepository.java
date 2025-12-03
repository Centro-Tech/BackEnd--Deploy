package school.sptech.projetoMima.repository.auxiliares;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projetoMima.entity.item.Tamanho;

public interface TamanhoRepository extends JpaRepository<Tamanho, Integer> {
    boolean existsByNomeIgnoreCase(String nome);
}
