package school.sptech.projetoMima.core.adapter.ItemVenda;

import school.sptech.projetoMima.core.domain.ItemVenda;

import java.util.List;
import java.util.Optional;

public interface ItemVendaGateway {
    ItemVenda save(ItemVenda itemVenda);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    List<ItemVenda> findAll();

    Optional<ItemVenda> findById(Integer id);

    List<ItemVenda> findByVendaIsNull(); // Novo método

    Optional<ItemVenda> buscarPorIdEVenda(Integer idItemVenda, Integer idVenda);
}
