package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.TamanhoGateway;
import school.sptech.projetoMima.core.application.command.Item.auxiliares.TamanhoCommad.AtualizarTamanhoCommand;
import school.sptech.projetoMima.core.application.exception.Item.auxiliares.TamanhoNaoEncontradoException;
import school.sptech.projetoMima.core.domain.item.Tamanho;

public class AtualizarTamanhoUseCase {

    private final TamanhoGateway tamanhoGateway;

    public AtualizarTamanhoUseCase(TamanhoGateway tamanhoGateway) {
        this.tamanhoGateway = tamanhoGateway;
    }

    public Tamanho execute(AtualizarTamanhoCommand command) {
        if (!tamanhoGateway.existsById(command.id())) {
            throw new TamanhoNaoEncontradoException("Tamanho com id " + command.id() + " não encontrado");
        }

        Tamanho tamanho = tamanhoGateway.findById(command.id());
        if (command.nome() != null && !command.nome().isBlank()) {
            tamanho.setNome(command.nome());
        }

        return tamanhoGateway.save(tamanho);
    }
}
