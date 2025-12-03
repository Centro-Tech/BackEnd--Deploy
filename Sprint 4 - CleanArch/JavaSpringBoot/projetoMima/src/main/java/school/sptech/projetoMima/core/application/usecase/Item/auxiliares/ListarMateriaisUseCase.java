package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.MaterialGateway;
import school.sptech.projetoMima.core.domain.item.Material;

import java.util.List;

public class ListarMateriaisUseCase {

    private final MaterialGateway materialGateway;

    public ListarMateriaisUseCase(MaterialGateway materialGateway) {
        this.materialGateway = materialGateway;
    }

    public List<Material> execute() {
        return materialGateway.findAll();
    }
}
