package school.sptech.projetoMima.core.application.usecase.Cliente;

import school.sptech.projetoMima.core.adapter.Cliente.ClienteGateway;
import school.sptech.projetoMima.core.application.command.Cliente.CadastrarClienteCommand;
import school.sptech.projetoMima.core.application.dto.clienteDto.ClienteMapper;
import school.sptech.projetoMima.core.domain.Cliente;

public class CadastrarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public CadastrarClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(CadastrarClienteCommand dto) {
        Cliente cliente = ClienteMapper.toEntity(dto);
        return clienteGateway.save(cliente);
    }
}
