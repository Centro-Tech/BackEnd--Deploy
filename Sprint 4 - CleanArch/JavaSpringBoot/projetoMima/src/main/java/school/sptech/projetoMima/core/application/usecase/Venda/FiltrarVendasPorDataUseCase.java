package school.sptech.projetoMima.core.application.usecase.Venda;

import school.sptech.projetoMima.core.adapter.Venda.VendaGateway;
import school.sptech.projetoMima.core.application.command.Venda.FiltrarVendasPorDataCommand;
import school.sptech.projetoMima.core.domain.Venda;

import java.util.List;

public class FiltrarVendasPorDataUseCase {

    private final VendaGateway vendaGateway;

    public FiltrarVendasPorDataUseCase(VendaGateway vendaGateway) {
        this.vendaGateway = vendaGateway;
    }

    public List<Venda> execute(FiltrarVendasPorDataCommand command) {

        if (command.dataInicio().isAfter(command.dataFim())) {
            throw new RuntimeException("Data de início não pode ser depois da data fim");
        }


        return vendaGateway.findByDataBetween(command.dataInicio(), command.dataFim());
    }
}
