package school.sptech.projetoMima.core.application.usecase.Item;

import school.sptech.projetoMima.core.application.exception.Item.ItemNaoEncontradoException;
import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.application.command.Item.BuscarItemPorIdCommand;
import school.sptech.projetoMima.core.domain.item.Item;

public class BuscarItemPorIdUseCase {

    private final ItemGateway itemGateway;

    public BuscarItemPorIdUseCase(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public Item execute(BuscarItemPorIdCommand command) {
        if (!itemGateway.existsById(command.id())) {
            throw new ItemNaoEncontradoException("Item não encontrado");
        }
        return itemGateway.findById(command.id());
    }
}
