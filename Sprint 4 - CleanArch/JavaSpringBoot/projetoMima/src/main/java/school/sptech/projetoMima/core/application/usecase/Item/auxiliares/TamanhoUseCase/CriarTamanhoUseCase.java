package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.TamanhoUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.TamanhoGateway;
import school.sptech.projetoMima.core.domain.item.Tamanho;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.TamanhoInvalidoException;
import school.sptech.projetoMima.core.application.exception.Item.Auxiliares.TamanhoDuplicadoException;

public class CriarTamanhoUseCase {
    private final TamanhoGateway gateway;

    public CriarTamanhoUseCase(TamanhoGateway gateway) {
        this.gateway = gateway;
    }

    public Tamanho execute(Tamanho tamanho) {
        if (tamanho == null || tamanho.getNome() == null || tamanho.getNome().isBlank()) {
            throw new TamanhoInvalidoException("Tamanho inválido");
        }

        if (gateway.existsByNome(tamanho.getNome())) {
            throw new TamanhoDuplicadoException("Tamanho já cadastrado");
        }

        return gateway.save(tamanho);
    }
}
