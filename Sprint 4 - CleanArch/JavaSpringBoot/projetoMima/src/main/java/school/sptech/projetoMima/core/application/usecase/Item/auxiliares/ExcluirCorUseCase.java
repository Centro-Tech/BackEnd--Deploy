package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CorGateway;
import school.sptech.projetoMima.core.application.command.Item.auxiliares.CorCommand.DeletarCorCommand;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CorNaoEncontradaException;

public class ExcluirCorUseCase {

    private final CorGateway corGateway;

    public ExcluirCorUseCase(CorGateway corGateway) {
        this.corGateway = corGateway;
    }

    @Transactional
    public void execute(DeletarCorCommand command) {
        if (!corGateway.existsById(command.id())) {
            throw new CorNaoEncontradaException("Cor com id " + command.id() + " não encontrada");
        }
        corGateway.deleteById(command.id());
    }
}
