package school.sptech.projetoMima.core.application.command.Venda;

public record RemoverItemDaVendaCommand(
        Integer itemId,
        Integer vendaId
) {
}
