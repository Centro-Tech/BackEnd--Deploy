package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CategoriaGateway;
import school.sptech.projetoMima.core.domain.item.Categoria;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CategoriaNaoEncontradaException;

public class BuscarCategoriaPorIdUseCase {
    private final CategoriaGateway categoriaGateway;

    public BuscarCategoriaPorIdUseCase(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public Categoria execute(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        Categoria categoria = categoriaGateway.findById(id);
        if (categoria == null) {
            throw new CategoriaNaoEncontradaException("Categoria não encontrada");
        }

        return categoria;
    }
}
