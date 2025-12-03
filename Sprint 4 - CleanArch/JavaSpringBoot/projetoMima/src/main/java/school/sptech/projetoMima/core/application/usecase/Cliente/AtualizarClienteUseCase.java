package school.sptech.projetoMima.core.application.usecase.Cliente;

import school.sptech.projetoMima.core.adapter.Cliente.ClienteGateway;
import school.sptech.projetoMima.core.application.command.Cliente.AtualizarClienteCommand;
import school.sptech.projetoMima.core.domain.Cliente;

public class AtualizarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public AtualizarClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(AtualizarClienteCommand command) {
        Integer id = command.id();

        if (!clienteGateway.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }

        Cliente clienteExistente = clienteGateway.findById(id);

        if (clienteExistente == null) {
            throw new RuntimeException("Cliente não encontrado");
        }

        if (command.nome() != null && !command.nome().trim().isEmpty()) {
            clienteExistente.setNome(command.nome().trim());
        }

        if (command.email() != null && !command.email().trim().isEmpty()) {
            clienteExistente.setEmail(command.email().trim());
        }

        if (command.cpf() != null && !command.cpf().trim().isEmpty()) {
            clienteExistente.setCPF(command.cpf().trim());
        }

        // Telefone pode ser nulo (cliente pode não ter telefone)
        if (command.telefone() != null) {
            clienteExistente.setTelefone(command.telefone().trim().isEmpty() ? null : command.telefone().trim());
        }

        if (command.endereco() != null && !command.endereco().trim().isEmpty()) {
            clienteExistente.setEndereco(command.endereco().trim());
        }

        return clienteGateway.save(clienteExistente);
    }
}
