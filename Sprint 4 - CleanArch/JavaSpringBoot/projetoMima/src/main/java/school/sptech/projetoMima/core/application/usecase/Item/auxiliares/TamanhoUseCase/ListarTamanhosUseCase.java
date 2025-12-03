package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.TamanhoUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.TamanhoGateway;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.TamanhoListaVaziaException;
import school.sptech.projetoMima.core.domain.item.Tamanho;

import java.util.List;

public class ListarTamanhosUseCase {
    private final TamanhoGateway gateway;

    public ListarTamanhosUseCase(TamanhoGateway gateway) {
        this.gateway = gateway;
    }

    public List<Tamanho> execute() {
        List<Tamanho> tamanhos = gateway.findAll();

        if (tamanhos.isEmpty()) {
            throw new TamanhoListaVaziaException("Nenhum tamanho encontrado");
        }

        return tamanhos;
    }
}
