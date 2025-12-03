package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.CategoriaUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CategoriaGateway;
import school.sptech.projetoMima.core.domain.item.Categoria;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CategoriaListaVaziaException;
import java.util.List;

public class ListarCategoriasUseCase {
    private final CategoriaGateway gateway;

    public ListarCategoriasUseCase(CategoriaGateway gateway) {
        this.gateway = gateway;
    }

    public List<Categoria> execute() {
        List<Categoria> categorias = gateway.findAll();

        if (categorias.isEmpty()) {
            throw new CategoriaListaVaziaException("Nenhuma categoria encontrada");
        }

        return categorias;
    }
}
