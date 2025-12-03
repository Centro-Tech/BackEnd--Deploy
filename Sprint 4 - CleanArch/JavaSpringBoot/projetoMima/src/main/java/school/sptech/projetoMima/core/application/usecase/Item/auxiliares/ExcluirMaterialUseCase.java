package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.MaterialGateway;
import school.sptech.projetoMima.core.application.command.Item.auxiliares.MaterialCommand.DeletarMaterialCommand;
import school.sptech.projetoMima.core.application.exception.Item.auxiliares.MaterialNaoEncontradoException;
import org.springframework.transaction.annotation.Transactional;

public class ExcluirMaterialUseCase {

    private final MaterialGateway materialGateway;

    public ExcluirMaterialUseCase(MaterialGateway materialGateway) {
        this.materialGateway = materialGateway;
    }

    @Transactional
    public void execute(DeletarMaterialCommand command) {
        if (!materialGateway.existsById(command.id())) {
            throw new MaterialNaoEncontradoException("Material com id " + command.id() + " não encontrado");
        }
        materialGateway.deleteById(command.id());
    }
}
