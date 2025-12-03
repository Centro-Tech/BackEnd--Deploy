package school.sptech.projetoMima.core.application.usecase.Item;

import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.application.command.Item.FiltrarItemPorFornecedorCommand;
import school.sptech.projetoMima.core.domain.item.Item;

import java.util.List;

public class FiltrarItemPorFornecedorUseCase {

    private final ItemGateway itemGateway;

    public FiltrarItemPorFornecedorUseCase(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public List<Item> execute(FiltrarItemPorFornecedorCommand command) {
        return itemGateway.findByFornecedorNomeContainsIgnoreCase(command.nomeFornecedor());
    }
}
