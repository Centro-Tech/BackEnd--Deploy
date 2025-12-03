package school.sptech.projetoMima.core.application.command.Fornecedor;

public record CadastrarFornecedorCommand(
        String nome,
        String telefone,
        String email) {
}
