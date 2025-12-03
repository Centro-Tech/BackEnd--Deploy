package school.sptech.projetoMima.core.application.command.Cliente;

public record CadastrarClienteCommand(
        String nome,
        String telefone,
        String CPF,
        String email,
        String endereco
) {}
