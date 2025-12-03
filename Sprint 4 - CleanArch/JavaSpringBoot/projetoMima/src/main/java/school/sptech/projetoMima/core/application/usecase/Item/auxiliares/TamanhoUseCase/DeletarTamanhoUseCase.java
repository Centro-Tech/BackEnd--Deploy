package school.sptech.projetoMima.core.application.usecase.Item.auxiliares.TamanhoUseCase;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.TamanhoGateway;

public class DeletarTamanhoUseCase {
    private final TamanhoGateway gateway;

    public DeletarTamanhoUseCase(TamanhoGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        
        if (!gateway.existsById(id)) {
            throw new school.sptech.projetoMima.core.application.exception.Item.auxiliares.TamanhoNaoEncontradoException("Tamanho não encontrado");
        }
        
        gateway.deleteById(id);
    }
}