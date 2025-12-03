package school.sptech.projetoMima.core.application.command.Venda;

public record FiltrarVendasPorValorCommand(
        Double valorMinimo,
        Double valorMaximo
) {
}
