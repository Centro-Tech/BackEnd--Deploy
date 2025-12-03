package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CorGateway;
import school.sptech.projetoMima.core.domain.item.Cor;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CorNaoEncontradaException;

public class BuscarCorPorIdUseCase {
    private final CorGateway corGateway;

    public BuscarCorPorIdUseCase(CorGateway corGateway) {
        this.corGateway = corGateway;
    }

    public Cor execute(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        Cor cor = corGateway.findById(id);
        if (cor == null) {
            throw new CorNaoEncontradaException("Cor não encontrada");
        }

        return cor;
    }
}
