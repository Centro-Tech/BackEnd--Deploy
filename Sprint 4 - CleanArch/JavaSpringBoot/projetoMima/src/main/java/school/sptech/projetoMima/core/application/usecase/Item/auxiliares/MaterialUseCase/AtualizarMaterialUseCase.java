package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.MaterialUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.MaterialGateway;
import school.sptech.projetoMima.core.domain.item.Material;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.MaterialInvalidoException;

public class AtualizarMaterialUseCase {
    private final MaterialGateway materialGateway;

    public AtualizarMaterialUseCase(MaterialGateway materialGateway) {
        this.materialGateway = materialGateway;
    }

    public Material execute(Integer id, String nome) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        if (nome == null || nome.isBlank()) {
            throw new MaterialInvalidoException("Nome do material não pode ser vazio");
        }

        Material material = materialGateway.findById(id);
        if (material == null) {
            throw new school.sptech.projetoMima.core.application.exception.Item.auxiliares.MaterialNaoEncontradoException("Material com id " + id + " não encontrado");
        }

        material.setNome(nome);
        return materialGateway.save(material);
    }
}
