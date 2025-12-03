package school.sptech.projetoMima.core.adapter.Fornecedor;

import school.sptech.projetoMima.core.domain.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FornecedorGateway {

    Fornecedor save(Fornecedor fornecedor);

    boolean existsByNome(String nome);

    boolean isNullOrEmpty(String str);

    boolean existsById(Integer id);

    Fornecedor findById(Integer id);

    List<Fornecedor> findAll();
    
    Page<Fornecedor> findAll(Pageable pageable);

    void deleteById(Integer id);

    Fornecedor update(Integer id, Fornecedor fornecedorAtualizado);
}
