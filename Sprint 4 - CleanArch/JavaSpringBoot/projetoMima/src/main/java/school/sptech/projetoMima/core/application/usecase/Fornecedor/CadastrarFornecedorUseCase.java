package school.sptech.projetoMima.core.application.usecase.Fornecedor;

import school.sptech.projetoMima.core.adapter.Fornecedor.FornecedorGateway;
import school.sptech.projetoMima.core.application.command.Fornecedor.CadastrarFornecedorCommand;
import school.sptech.projetoMima.core.application.exception.Fornecedor.FornecedorExistenteException;
import school.sptech.projetoMima.core.domain.Fornecedor;

public class CadastrarFornecedorUseCase {

    private final FornecedorGateway gateway;

    public CadastrarFornecedorUseCase(FornecedorGateway gateway) {
        this.gateway = gateway;
    }

    public Fornecedor execute (CadastrarFornecedorCommand command) {

        if (gateway.existsByNome(command.nome())) {
            throw new FornecedorExistenteException("Já existe um fornecedor com esse nome");
        }

        if (gateway.isNullOrEmpty(command.nome()) || gateway.isNullOrEmpty(command.email()) || gateway.isNullOrEmpty(command.telefone())) {
            throw new IllegalArgumentException("Todos os campos são de preenchimento obrigatório");
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(command.nome());
        fornecedor.setTelefone(command.telefone());
        fornecedor.setEmail(command.email());

        return gateway.save(fornecedor);
    }
}
