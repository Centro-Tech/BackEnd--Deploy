package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.TamanhoGateway;
import school.sptech.projetoMima.core.application.command.Item.auxiliares.TamanhoCommad.CriarTamanhoCommand;
import school.sptech.projetoMima.core.application.exception.Item.auxiliares.TamanhoNaoEncontradoException;
import school.sptech.projetoMima.core.domain.item.Tamanho;

public class CriarTamanhoUseCase {

    private final TamanhoGateway tamanhoGateway;

    public CriarTamanhoUseCase(TamanhoGateway tamanhoGateway) {
        this.tamanhoGateway = tamanhoGateway;
    }

    public Tamanho execute(CriarTamanhoCommand command) {
        if (command.nome() == null || command.nome().isBlank()) {
            throw new IllegalArgumentException("Nome do tamanho não pode ser vazio");
        }

        if (tamanhoGateway.existsByNome(command.nome())) {
            throw new IllegalArgumentException("Já existe um tamanho com este nome");
        }

        Tamanho tamanho = new Tamanho();
        tamanho.setNome(command.nome());

        return tamanhoGateway.save(tamanho);
    }
}
