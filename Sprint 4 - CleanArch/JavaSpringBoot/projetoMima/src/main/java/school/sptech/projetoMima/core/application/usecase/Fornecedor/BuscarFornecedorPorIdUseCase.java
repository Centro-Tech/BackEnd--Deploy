package school.sptech.projetoMima.core.application.usecase.Fornecedor;

import school.sptech.projetoMima.core.adapter.Fornecedor.FornecedorGateway;
import school.sptech.projetoMima.core.application.exception.Fornecedor.FornecedorNaoEncontradoException;
import school.sptech.projetoMima.core.domain.Fornecedor;

public class BuscarFornecedorPorIdUseCase {

    private final FornecedorGateway gateway;

    public BuscarFornecedorPorIdUseCase(FornecedorGateway gateway) {
        this.gateway = gateway;
    }

    public Fornecedor execute (Integer id) {

        if (!gateway.existsById(id)) {
            throw new FornecedorNaoEncontradoException("Fornecedor não encontrado");
        }
        return gateway.findById(id);
    }
}
