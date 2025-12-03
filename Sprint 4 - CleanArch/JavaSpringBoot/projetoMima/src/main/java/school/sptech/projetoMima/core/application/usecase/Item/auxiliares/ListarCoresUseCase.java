package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CorGateway;
import school.sptech.projetoMima.core.domain.item.Cor;

import java.util.List;

public class ListarCoresUseCase {

    private final CorGateway corGateway;

    public ListarCoresUseCase(CorGateway corGateway) {
        this.corGateway = corGateway;
    }

    public List<Cor> execute() {
        return corGateway.findAll();
    }
}
