package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.CategoriaUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CategoriaGateway;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CategoriaNaoEncontradaException;

public class DeletarCategoriaUseCase {
    private final CategoriaGateway gateway;

    public DeletarCategoriaUseCase(CategoriaGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        if (!gateway.existsById(id)) {
            throw new CategoriaNaoEncontradaException("Categoria não encontrada");
        }

        gateway.deleteById(id);
    }
}
