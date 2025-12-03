package school.sptech.projetoMima.core.application.usecase.Cliente;

import school.sptech.projetoMima.core.adapter.Cliente.ClienteGateway;
import school.sptech.projetoMima.core.application.command.Cliente.BuscarClientePorIdCommand;
import school.sptech.projetoMima.core.application.exception.Cliente.ClienteNaoEncontradoException;
import school.sptech.projetoMima.core.domain.Cliente;

public class BuscarClientePorIdUseCase {

    private final ClienteGateway clienteGateway;

    public BuscarClientePorIdUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(BuscarClientePorIdCommand command) {
        Integer id = command.id();
        if (!clienteGateway.existsById(id)) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }
        return clienteGateway.findById(id);
    }
}
