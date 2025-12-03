package school.sptech.projetoMima.core.application.usecase.Cliente;

import school.sptech.projetoMima.core.adapter.Cliente.ClienteGateway;
import school.sptech.projetoMima.core.application.command.Cliente.ExcluirClienteCommand;
import school.sptech.projetoMima.core.application.exception.Cliente.ClienteNaoEncontradoException;

public class ExcluirClienteUseCase {

    private final ClienteGateway clienteGateway;

    public ExcluirClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public void execute(ExcluirClienteCommand id) {
        if (!clienteGateway.existsById(id.id())) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }
        clienteGateway.deleteById(id.id());
    }
}
