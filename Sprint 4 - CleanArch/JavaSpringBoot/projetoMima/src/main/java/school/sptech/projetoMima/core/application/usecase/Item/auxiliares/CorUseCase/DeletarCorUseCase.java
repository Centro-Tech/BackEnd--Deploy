package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.CorUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CorGateway;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CorNaoEncontradaException;

public class DeletarCorUseCase {
    private final CorGateway gateway;

    public DeletarCorUseCase(CorGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        if (!gateway.existsById(id)) {
            throw new CorNaoEncontradaException("Cor não encontrada");
        }

        gateway.deleteById(id);
    }
}
