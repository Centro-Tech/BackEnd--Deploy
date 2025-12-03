package school.sptech.projetoMima.core.application.usecase.Item;

import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.application.command.Item.FiltrarItemPorNomeCommand;
import school.sptech.projetoMima.core.domain.item.Item;

import java.util.List;

public class FiltrarItemPorNomeUseCase {

    private final ItemGateway itemGateway;

    public FiltrarItemPorNomeUseCase(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public List<Item> execute(FiltrarItemPorNomeCommand command) {
        return itemGateway.findByNomeContainsIgnoreCase(command.nome());
    }
}
