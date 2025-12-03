package school.sptech.projetoMima.core.application.usecase.Item;

import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.application.command.Item.FiltrarItemPorCategoriaCommand;
import school.sptech.projetoMima.core.domain.item.Item;

import java.util.List;

public class FiltrarItemPorCategoriaUseCase {

    private final ItemGateway itemGateway;

    public FiltrarItemPorCategoriaUseCase(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public List<Item> execute(FiltrarItemPorCategoriaCommand command) {
        return itemGateway.findByCategoriaNomeContainsIgnoreCase(command.nomeCategoria());
    }
}
