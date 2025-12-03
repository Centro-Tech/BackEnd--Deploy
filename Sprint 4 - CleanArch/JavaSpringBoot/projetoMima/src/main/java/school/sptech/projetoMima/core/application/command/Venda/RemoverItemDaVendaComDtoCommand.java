package school.sptech.projetoMima.core.application.command.Venda;

public record RemoverItemDaVendaComDtoCommand(
        Integer vendaId,
        Integer itemVendaId
) {
}
