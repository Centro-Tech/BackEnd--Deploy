package school.sptech.projetoMima.core.application.usecase.Item;

import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.application.exception.Item.ItemNaoEncontradoException;
import school.sptech.projetoMima.core.application.command.Item.ExcluirItemCommand;

public class ExcluirItemUseCase {

    private final ItemGateway itemGateway;

    public ExcluirItemUseCase(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public void execute(ExcluirItemCommand command) {
        if (!itemGateway.existsById(command.id())) {
            throw new ItemNaoEncontradoException("Item com id " + command.id() + " não encontrado");
        }
        itemGateway.deleteById(command.id());
    }
}
