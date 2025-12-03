package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.MaterialGateway;
import school.sptech.projetoMima.core.application.exception.Item.auxiliares.MaterialNaoEncontradoException;
import school.sptech.projetoMima.core.domain.item.Material;

public class BuscarMaterialPorIdUseCase {

    private final MaterialGateway materialGateway;

    public BuscarMaterialPorIdUseCase(MaterialGateway materialGateway) {
        this.materialGateway = materialGateway;
    }

    public Material execute(Integer id) {
        if (!materialGateway.existsById(id)) {
            throw new MaterialNaoEncontradoException("Material não encontrado");
        }
        return materialGateway.findById(id);
    }
}
