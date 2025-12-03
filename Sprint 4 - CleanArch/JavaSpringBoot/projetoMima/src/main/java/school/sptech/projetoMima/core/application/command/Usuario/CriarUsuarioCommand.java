package school.sptech.projetoMima.core.application.command.Usuario;

public record CriarUsuarioCommand(
        String nome,
        String email,
        String senha,
        String telefone,
        String cargo,
        String endereco,
        String imagem
) {}
