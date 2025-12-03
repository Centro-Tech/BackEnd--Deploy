package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.MaterialUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.MaterialGateway;
import school.sptech.projetoMima.core.domain.item.Material;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.MaterialInvalidoException;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.MaterialDuplicadoException;

public class CriarMaterialUseCase {
    private final MaterialGateway gateway;

    public CriarMaterialUseCase(MaterialGateway gateway) {
        this.gateway = gateway;
    }

    public Material execute(Material material) {
        if (material == null || material.getNome() == null || material.getNome().isBlank()) {
            throw new MaterialInvalidoException("Material inválido");
        }

        if (gateway.existsByNome(material.getNome())) {
            throw new MaterialDuplicadoException("Material já cadastrado");
        }

        return gateway.save(material);
    }
}
