package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.TamanhoGateway;
import school.sptech.projetoMima.core.domain.item.Tamanho;

import java.util.List;

public class ListarTamanhosUseCase {

    private final TamanhoGateway tamanhoGateway;

    public ListarTamanhosUseCase(TamanhoGateway tamanhoGateway) {
        this.tamanhoGateway = tamanhoGateway;
    }

    public List<Tamanho> execute() {
        return tamanhoGateway.findAll();
    }
}
