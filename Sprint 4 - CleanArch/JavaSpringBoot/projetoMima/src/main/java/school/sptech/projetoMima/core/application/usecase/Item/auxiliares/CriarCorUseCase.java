package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CorGateway;
import school.sptech.projetoMima.core.application.command.Item.auxiliares.CorCommand.CriarCorCommand;
import school.sptech.projetoMima.core.domain.item.Cor;
import org.springframework.transaction.annotation.Transactional;

public class CriarCorUseCase {

    private final CorGateway corGateway;

    public CriarCorUseCase(CorGateway corGateway) {
        this.corGateway = corGateway;
    }

    @Transactional
    public Cor execute(CriarCorCommand command) {
        if (command.nome() == null || command.nome().isBlank()) {
            throw new IllegalArgumentException("Nome da cor não pode ser vazio");
        }

        if (corGateway.existsByNome(command.nome())) {
            throw new IllegalArgumentException("Já existe uma cor com este nome");
        }

        Cor cor = new Cor();
        cor.setNome(command.nome());

        return corGateway.save(cor);
    }
}
