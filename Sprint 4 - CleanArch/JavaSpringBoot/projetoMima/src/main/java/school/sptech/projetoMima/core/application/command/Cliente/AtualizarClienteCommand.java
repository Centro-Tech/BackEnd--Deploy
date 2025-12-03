package school.sptech.projetoMima.core.application.command.Cliente;

public record AtualizarClienteCommand(
        Integer id,
        String nome,
        String email,
        String cpf,
        String telefone,
        String endereco
) {
}
