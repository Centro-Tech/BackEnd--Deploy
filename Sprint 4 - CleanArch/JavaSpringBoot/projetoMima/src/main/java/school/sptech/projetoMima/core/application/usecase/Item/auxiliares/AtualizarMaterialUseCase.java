package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.MaterialGateway;
import school.sptech.projetoMima.core.application.command.Item.auxiliares.MaterialCommand.AtualizarMaterialCommand;
import school.sptech.projetoMima.core.application.exception.Item.auxiliares.MaterialNaoEncontradoException;
import school.sptech.projetoMima.core.domain.item.Material;

public class AtualizarMaterialUseCase {

    private final MaterialGateway materialGateway;

    public AtualizarMaterialUseCase(MaterialGateway materialGateway) {
        this.materialGateway = materialGateway;
    }

    public Material execute(AtualizarMaterialCommand command) {
        if (!materialGateway.existsById(command.id())) {
            throw new MaterialNaoEncontradoException("Material com id " + command.id() + " não encontrado");
        }

        Material material = materialGateway.findById(command.id());
        if (command.nome() != null && !command.nome().isBlank()) {
            material.setNome(command.nome());
        }

        return materialGateway.save(material);
    }
}
