package school.sptech.projetoMima.core.application.usecase.Item;

import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.application.command.Item.BuscarItemPorCodigoCommand;
import school.sptech.projetoMima.core.application.exception.Item.ItemNaoEncontradoException;
import school.sptech.projetoMima.core.domain.item.Item;

public class BuscarItemPorCodigoUseCase {

    private final ItemGateway itemGateway;

    public BuscarItemPorCodigoUseCase(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public Item execute(BuscarItemPorCodigoCommand command) {
        if (!itemGateway.existsByCodigo(command.codigo())) {
            throw new ItemNaoEncontradoException("Item com código " + command.codigo() + " não encontrado");
        }
        return itemGateway.findByCodigo(command.codigo());
    }
}
