package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.MaterialUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.MaterialGateway;
import school.sptech.projetoMima.core.domain.item.Material;

public class BuscarMaterialPorIdUseCase {
    private final MaterialGateway gateway;

    public BuscarMaterialPorIdUseCase(MaterialGateway gateway) {
        this.gateway = gateway;
    }

    public Material execute(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        Material material = gateway.findById(id);

        if (material == null) {
            throw new school.sptech.projetoMima.core.application.exception.Item.auxiliares.MaterialNaoEncontradoException("Material não encontrado");
        }

        return material;
    }
}
