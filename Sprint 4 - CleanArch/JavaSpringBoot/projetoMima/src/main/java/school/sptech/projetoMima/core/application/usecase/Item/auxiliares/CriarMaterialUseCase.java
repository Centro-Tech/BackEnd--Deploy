package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.MaterialGateway;
import school.sptech.projetoMima.core.application.command.Item.auxiliares.MaterialCommand.CriarMaterialCommand;
import school.sptech.projetoMima.core.domain.item.Material;
import org.springframework.transaction.annotation.Transactional;

public class CriarMaterialUseCase {

    private final MaterialGateway materialGateway;

    public CriarMaterialUseCase(MaterialGateway materialGateway) {
        this.materialGateway = materialGateway;
    }

    @Transactional
    public Material execute(CriarMaterialCommand command) {
        if (command.nome() == null || command.nome().isBlank()) {
            throw new IllegalArgumentException("Nome do material não pode ser vazio");
        }

        if (materialGateway.existsByNome(command.nome())) {
            throw new IllegalArgumentException("Já existe um material com este nome");
        }

        Material material = new Material();
        material.setNome(command.nome());

        return materialGateway.save(material);
    }
}
