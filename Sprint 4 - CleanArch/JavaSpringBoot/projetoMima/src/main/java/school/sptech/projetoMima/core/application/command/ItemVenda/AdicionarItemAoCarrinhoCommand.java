package school.sptech.projetoMima.core.application.command.ItemVenda;

public record AdicionarItemAoCarrinhoCommand(
        Integer itemId,
        Integer clienteId,
        Integer funcionarioId,
        Integer qtdParaVender
) {
}
