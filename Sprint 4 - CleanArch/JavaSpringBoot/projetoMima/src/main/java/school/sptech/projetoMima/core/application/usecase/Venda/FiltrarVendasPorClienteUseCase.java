package school.sptech.projetoMima.core.application.usecase.Venda;

import school.sptech.projetoMima.core.adapter.Venda.VendaGateway;
import school.sptech.projetoMima.core.application.command.Venda.FiltrarVendasPorClienteCommand;
import school.sptech.projetoMima.core.domain.Venda;

import java.util.List;

public class FiltrarVendasPorClienteUseCase {

    private final VendaGateway vendaGateway;

    public FiltrarVendasPorClienteUseCase(VendaGateway vendaGateway) {
        this.vendaGateway = vendaGateway;
    }

    public List<Venda> execute(FiltrarVendasPorClienteCommand command) {

        return vendaGateway.findByClienteId(command.clienteId());
    }
}
