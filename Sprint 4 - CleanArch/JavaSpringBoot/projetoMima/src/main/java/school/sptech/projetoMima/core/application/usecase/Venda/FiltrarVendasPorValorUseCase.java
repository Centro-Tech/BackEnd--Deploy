package school.sptech.projetoMima.core.application.usecase.Venda;

import school.sptech.projetoMima.core.adapter.Venda.VendaGateway;
import school.sptech.projetoMima.core.application.command.Venda.FiltrarVendasPorValorCommand;
import school.sptech.projetoMima.core.domain.Venda;

import java.util.List;

public class FiltrarVendasPorValorUseCase {

    private final VendaGateway vendaGateway;

    public FiltrarVendasPorValorUseCase(VendaGateway vendaGateway) {
        this.vendaGateway = vendaGateway;
    }

    public List<Venda> execute(FiltrarVendasPorValorCommand command) {

        if (command.valorMinimo() < 0 || command.valorMaximo() < 0) {
            throw new RuntimeException("Valores não podem ser negativos");
        }

        if (command.valorMinimo() > command.valorMaximo()) {
            throw new RuntimeException("Valor mínimo não pode ser maior que o máximo");
        }

        return vendaGateway.findByValorTotalBetween(command.valorMinimo(), command.valorMaximo());
    }
}
