package school.sptech.projetoMima.core.adapter.Item;

import school.sptech.projetoMima.core.domain.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemGateway {
    Item save(Item item);

    boolean existsByCodigo(String codigo);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    void deleteByCodigo(String codigo);

    List<Item> findAll();
    
    Page<Item> findAll(Pageable pageable);

    Item findById(Integer id);

    Item findByCodigo(String codigo);

    List<Item> findByCategoriaNomeContainsIgnoreCase(String nomeCategoria);

    List<Item> findByFornecedorNomeContainsIgnoreCase(String nome);

    List<Item> findByNomeContainsIgnoreCase(String nome);

    List<Item> findByCodigoContainsIgnoreCase(String codigo);
    
    boolean existsByMaterialId(Integer materialId);
}
