package school.sptech.projetoMima.core.application.command.Usuario;

public record AtualizarUsuarioCommand(
        Integer id,
        String nome,
        String email,
        String telefone,
        String cargo,
        String endereco,
        String imagem
) {}
