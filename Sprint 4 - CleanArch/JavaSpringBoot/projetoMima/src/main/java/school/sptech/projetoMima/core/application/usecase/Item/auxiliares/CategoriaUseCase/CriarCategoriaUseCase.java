package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.CategoriaUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CategoriaGateway;
import school.sptech.projetoMima.core.domain.item.Categoria;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CategoriaInvalidaException;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CategoriaDuplicadaException;

public class CriarCategoriaUseCase {
    private final CategoriaGateway gateway;

    public CriarCategoriaUseCase(CategoriaGateway gateway) {
        this.gateway = gateway;
    }

    public Categoria execute(Categoria categoria) {
        if (categoria == null || categoria.getNome() == null || categoria.getNome().isBlank()) {
            throw new CategoriaInvalidaException("Categoria inválida");
        }

        if (gateway.existsByNomeIgnoreCase(categoria.getNome())) {
            throw new CategoriaDuplicadaException("Categoria já cadastrada");
        }

        return gateway.save(categoria);
    }
}
