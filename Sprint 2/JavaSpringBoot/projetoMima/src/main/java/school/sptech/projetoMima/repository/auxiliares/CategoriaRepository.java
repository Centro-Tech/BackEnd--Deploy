package school.sptech.projetoMima.repository.auxiliares;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projetoMima.entity.item.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    boolean existsByNomeIgnoreCase(String nome);

}
