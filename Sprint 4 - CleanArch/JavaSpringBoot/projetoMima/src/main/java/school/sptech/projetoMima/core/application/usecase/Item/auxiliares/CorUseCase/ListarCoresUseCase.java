package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.CorUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CorGateway;
import school.sptech.projetoMima.core.domain.item.Cor;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CorListaVaziaException;

import java.util.List;

public class ListarCoresUseCase {
    private final CorGateway gateway;

    public ListarCoresUseCase(CorGateway gateway) {
        this.gateway = gateway;
    }

    public List<Cor> execute() {
        List<Cor> cores = gateway.findAll();

        if (cores.isEmpty()) {
            throw new CorListaVaziaException("Nenhuma cor encontrada");
        }

        return cores;
    }
}
