package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.CategoriaUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CategoriaGateway;
import school.sptech.projetoMima.core.domain.item.Categoria;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CategoriaNaoEncontradaException;

public class BuscarCategoriaPorIdUseCase {

    private final CategoriaGateway gateway;

    public BuscarCategoriaPorIdUseCase(CategoriaGateway gateway) {
        this.gateway = gateway;
    }

    public Categoria execute(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        Categoria categoria = gateway.findById(id);

        if (categoria == null) {
            throw new CategoriaNaoEncontradaException("Categoria não encontrada");
        }

        return categoria;
    }
}
