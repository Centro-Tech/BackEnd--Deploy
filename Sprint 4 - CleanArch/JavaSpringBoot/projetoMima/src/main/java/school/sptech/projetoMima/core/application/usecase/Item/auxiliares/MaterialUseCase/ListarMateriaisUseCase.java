package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.MaterialUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.MaterialGateway;
import school.sptech.projetoMima.core.domain.item.Material;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.MaterialListaVaziaException;

import java.util.List;

public class ListarMateriaisUseCase {
    private final MaterialGateway gateway;

    public ListarMateriaisUseCase(MaterialGateway gateway) {
        this.gateway = gateway;
    }

    public List<Material> execute() {
        List<Material> materiais = gateway.findAll();

        if (materiais.isEmpty()) {
            throw new MaterialListaVaziaException("Nenhum material encontrado");
        }

        return materiais;
    }
}
