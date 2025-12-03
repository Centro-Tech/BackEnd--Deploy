package school.sptech.projetoMima.core.application.usecase.Fornecedor;

import school.sptech.projetoMima.core.adapter.Fornecedor.FornecedorGateway;
import school.sptech.projetoMima.core.application.exception.Cliente.ClienteListaVaziaException;
import school.sptech.projetoMima.core.application.exception.Fornecedor.SemFornecedorCadastradoException;
import school.sptech.projetoMima.core.domain.Cliente;
import school.sptech.projetoMima.core.domain.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ListarFornecedoresUseCase {

    private final FornecedorGateway gateway;

    public ListarFornecedoresUseCase(FornecedorGateway gateway) {
        this.gateway = gateway;
    }

    public List<Fornecedor> execute () {
        List<Fornecedor> fornecedores = gateway.findAll();

        if (fornecedores.isEmpty()) {
            throw new SemFornecedorCadastradoException("Lista de fornecedores está vazia");
        }
        return fornecedores;
    }

    public Page<Fornecedor> execute(Pageable pageable) {
        return gateway.findAll(pageable);
    }
}
