package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.CorUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CorGateway;
import school.sptech.projetoMima.core.domain.item.Cor;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CorNaoEncontradaException;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.CorInvalidaException;
import org.springframework.transaction.annotation.Transactional;

public class AtualizarCorUseCase {
    private final CorGateway corGateway;

    public AtualizarCorUseCase(CorGateway corGateway) {
        this.corGateway = corGateway;
    }

    @Transactional
    public Cor execute(Integer id, String nome) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        if (nome == null || nome.isBlank()) {
            throw new CorInvalidaException("Nome da cor não pode ser vazio");
        }

        Cor cor = corGateway.findById(id);
        if (cor == null) {
            throw new CorNaoEncontradaException("Cor com id " + id + " não encontrada");
        }

        cor.setNome(nome);
        return corGateway.save(cor);
    }
}
