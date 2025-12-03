package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CategoriaGateway;
import school.sptech.projetoMima.core.domain.item.Categoria;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CategoriaNaoEncontradaException;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CategoriaInvalidaException;

public class AtualizarCategoriaUseCase {
    private final CategoriaGateway categoriaGateway;

    public AtualizarCategoriaUseCase(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public Categoria execute(Integer id, String nome) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        if (nome == null || nome.isBlank()) {
            throw new CategoriaInvalidaException("Nome da categoria não pode ser vazio");
        }

        Categoria categoria = categoriaGateway.findById(id);
        if (categoria == null) {
            throw new CategoriaNaoEncontradaException("Categoria com id " + id + " não encontrada");
        }

        categoria.setNome(nome);
        return categoriaGateway.save(categoria);
    }
}
