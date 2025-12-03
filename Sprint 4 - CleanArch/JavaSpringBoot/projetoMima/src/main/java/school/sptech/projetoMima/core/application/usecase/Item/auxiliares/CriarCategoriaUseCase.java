package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CategoriaGateway;
import school.sptech.projetoMima.core.application.command.Item.auxiliares.CategoriaCommand.CriarCategoriaCommand;
import school.sptech.projetoMima.core.domain.item.Categoria;
import org.springframework.transaction.annotation.Transactional;

public class CriarCategoriaUseCase {

    private final CategoriaGateway categoriaGateway;

    public CriarCategoriaUseCase(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    @Transactional
    public Categoria execute(CriarCategoriaCommand command) {
        if (command.nome() == null || command.nome().isBlank()) {
            throw new IllegalArgumentException("Nome da categoria não pode ser vazio");
        }

        if (categoriaGateway.existsByNome(command.nome())) {
            throw new IllegalArgumentException("Já existe uma categoria com este nome");
        }

        Categoria categoria = new Categoria();
        categoria.setNome(command.nome());

        return categoriaGateway.save(categoria);
    }
}
