package school.sptech.projetoMima.core.application.usecase.Venda;

import school.sptech.projetoMima.core.adapter.Cliente.ClienteGateway;
import school.sptech.projetoMima.core.adapter.Venda.VendaGateway;
import school.sptech.projetoMima.core.application.command.Venda.CriarVendaCommand;
import school.sptech.projetoMima.core.application.exception.Cliente.ClienteNaoEncontradoException;
import school.sptech.projetoMima.core.domain.Cliente;
import school.sptech.projetoMima.core.domain.Venda;

import java.time.LocalDateTime;

public class CadastrarVendaUseCase {

    private final VendaGateway vendaGateway;
    private final ClienteGateway clienteGateway;

    public CadastrarVendaUseCase(VendaGateway vendaGateway,
                                 ClienteGateway clienteGateway) {
        this.vendaGateway = vendaGateway;
        this.clienteGateway = clienteGateway;
    }

    public Venda executar(CriarVendaCommand command) {

        Cliente cliente = clienteGateway.findById(command.clienteId());
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setData(LocalDateTime.now());
        venda.setValorTotal(command.valorTotal());

        return vendaGateway.save(venda);
    }
}
