package school.sptech.projetoMima.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.projetoMima.entity.Fornecedor;

import java.util.List;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    boolean existsByNome(String nome);

    List<Fornecedor> findByNomeEqualsIgnoreCase(String nome);
}
