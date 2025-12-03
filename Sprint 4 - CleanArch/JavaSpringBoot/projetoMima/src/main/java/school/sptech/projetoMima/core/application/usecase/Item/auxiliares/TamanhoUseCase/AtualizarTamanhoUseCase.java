package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.TamanhoUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.TamanhoGateway;
import school.sptech.projetoMima.core.domain.item.Tamanho;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.TamanhoInvalidoException;

public class AtualizarTamanhoUseCase {
    private final TamanhoGateway tamanhoGateway;

    public AtualizarTamanhoUseCase(TamanhoGateway tamanhoGateway) {
        this.tamanhoGateway = tamanhoGateway;
    }

    public Tamanho execute(Integer id, String nome) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        if (nome == null || nome.isBlank()) {
            throw new TamanhoInvalidoException("Nome do tamanho não pode ser vazio");
        }

        Tamanho tamanho = tamanhoGateway.findById(id);
        if (tamanho == null) {
            throw new school.sptech.projetoMima.core.application.exception.Item.auxiliares.TamanhoNaoEncontradoException("Tamanho com id " + id + " não encontrado");
        }

        tamanho.setNome(nome);
        return tamanhoGateway.save(tamanho);
    }
}
