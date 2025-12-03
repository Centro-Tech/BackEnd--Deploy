package school.sptech.projetoMima.core.application.command.ItemVenda;

public record FinalizarCarrinhoCommand(
        Integer clienteId,
        Integer funcionarioId
) {
}
