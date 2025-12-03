package school.sptech.projetoMima.core.application.usecase.Item.auxiliares;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.TamanhoGateway;
import school.sptech.projetoMima.core.application.exception.Item.auxiliares.TamanhoNaoEncontradoException;
import school.sptech.projetoMima.core.domain.item.Tamanho;

public class BuscarTamanhoPorIdUseCase {

    private final TamanhoGateway tamanhoGateway;

    public BuscarTamanhoPorIdUseCase(TamanhoGateway tamanhoGateway) {
        this.tamanhoGateway = tamanhoGateway;
    }

    public Tamanho execute(Integer id) {
        if (!tamanhoGateway.existsById(id)) {
            throw new TamanhoNaoEncontradoException("Tamanho não encontrado");
        }
        return tamanhoGateway.findById(id);
    }
}
