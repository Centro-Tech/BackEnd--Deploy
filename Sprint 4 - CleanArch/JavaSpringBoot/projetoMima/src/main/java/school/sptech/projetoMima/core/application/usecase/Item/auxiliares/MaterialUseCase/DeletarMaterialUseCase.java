package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.MaterialUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.MaterialGateway;
import school.sptech.projetoMima.core.adapter.Item.ItemGateway;

public class DeletarMaterialUseCase {
    private final MaterialGateway gateway;
    private final ItemGateway itemGateway;

    public DeletarMaterialUseCase(MaterialGateway gateway, ItemGateway itemGateway) {
        this.gateway = gateway;
        this.itemGateway = itemGateway;
    }

    public void execute(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        if (!gateway.existsById(id)) {
            throw new school.sptech.projetoMima.core.application.exception.Item.auxiliares.MaterialNaoEncontradoException("Material não encontrado");
        }
        
        if (itemGateway.existsByMaterialId(id)) {
            throw new IllegalArgumentException("Não é possível excluir o material pois existem itens que o utilizam");
        }

        gateway.deleteById(id);
    }
}
