package school.sptech.projetoMima.core.application.usecase.Fornecedor;

import school.sptech.projetoMima.core.adapter.Fornecedor.FornecedorGateway;
import school.sptech.projetoMima.core.application.exception.Fornecedor.FornecedorNaoEncontradoException;

public class DeletarFornecedorUseCase {

    private final FornecedorGateway gateway;

    public DeletarFornecedorUseCase(FornecedorGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(Integer id) {
        if (!gateway.existsById(id)) {
            throw new FornecedorNaoEncontradoException("Fornecedor não encontrado");
        }
        gateway.deleteById(id);
    }
}
