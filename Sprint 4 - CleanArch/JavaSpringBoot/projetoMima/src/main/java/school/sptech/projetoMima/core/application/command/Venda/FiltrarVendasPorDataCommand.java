package school.sptech.projetoMima.core.application.command.Venda;

import java.time.LocalDate;

public record FiltrarVendasPorDataCommand(
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
