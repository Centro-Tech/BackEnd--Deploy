package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.TamanhoUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.TamanhoGateway;
import school.sptech.projetoMima.core.domain.item.Tamanho;

public class BuscarTamanhoPorIdUseCase {
    private final TamanhoGateway gateway;

    public BuscarTamanhoPorIdUseCase(TamanhoGateway gateway) {
        this.gateway = gateway;
    }

    public Tamanho execute(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        Tamanho tamanho = gateway.findById(id);

        if (tamanho == null) {
            throw new school.sptech.projetoMima.core.application.exception.Item.auxiliares.TamanhoNaoEncontradoException("Tamanho não encontrado");
        }

        return tamanho;
    }
}
