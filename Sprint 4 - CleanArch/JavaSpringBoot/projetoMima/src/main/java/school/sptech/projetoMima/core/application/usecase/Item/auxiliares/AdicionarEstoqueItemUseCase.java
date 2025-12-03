package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.application.command.Item.AdicionarEstoqueItemCommand;
import school.sptech.projetoMima.core.application.exception.Item.ItemNaoEncontradoException;
import school.sptech.projetoMima.core.domain.item.Item;

public class AdicionarEstoqueItemUseCase {

    private final ItemGateway itemGateway;

    public AdicionarEstoqueItemUseCase(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public Item execute(AdicionarEstoqueItemCommand command) {
        if (!itemGateway.existsById(command.id())) {
            throw new ItemNaoEncontradoException("Item com id " + command.id() + " não encontrado");
        }

        if (command.quantidade() == null || command.quantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        Item item = itemGateway.findById(command.id());
        int novaQuantidade = item.getQtdEstoque() + command.quantidade();
        item.setQtdEstoque(novaQuantidade);

        return itemGateway.save(item);
    }
}
