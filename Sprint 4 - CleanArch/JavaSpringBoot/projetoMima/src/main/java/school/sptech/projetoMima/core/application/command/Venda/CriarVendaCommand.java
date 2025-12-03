package school.sptech.projetoMima.core.application.command.Venda;

import java.util.List;

public record CriarVendaCommand(
        Double valorTotal,
        Integer clienteId,
        List<Integer> itensVenda,
        Integer funcionarioId
) {
}
