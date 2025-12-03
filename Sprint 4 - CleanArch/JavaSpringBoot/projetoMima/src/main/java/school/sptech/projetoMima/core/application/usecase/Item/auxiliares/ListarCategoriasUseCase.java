package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CategoriaGateway;
import school.sptech.projetoMima.core.domain.item.Categoria;

import java.util.List;

public class ListarCategoriasUseCase {

    private final CategoriaGateway categoriaGateway;

    public ListarCategoriasUseCase(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public List<Categoria> execute() {
        return categoriaGateway.findAll();
    }
}
