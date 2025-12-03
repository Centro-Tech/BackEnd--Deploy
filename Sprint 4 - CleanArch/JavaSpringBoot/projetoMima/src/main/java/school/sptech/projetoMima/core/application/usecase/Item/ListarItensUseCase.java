package school.sptech.projetoMima.core.application.usecase.Item;

import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.domain.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ListarItensUseCase {

    private final ItemGateway itemGateway;

    public ListarItensUseCase(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public List<Item> execute() {
        return itemGateway.findAll();
    }

    public Page<Item> execute(Pageable pageable) {
        return itemGateway.findAll(pageable);
    }
}
