package school.sptech.projetoMima.core.application.usecase.Item;

import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.domain.item.Item;

import java.util.List;

public class ListarEstoqueUseCase {

    private final ItemGateway itemGateway;

    public ListarEstoqueUseCase(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public List<Item> execute() {
        return itemGateway.findAll()
                .stream()
                .filter(item -> item.getQtdEstoque() != null && item.getQtdEstoque() > 0)
                .toList();
    }
}
