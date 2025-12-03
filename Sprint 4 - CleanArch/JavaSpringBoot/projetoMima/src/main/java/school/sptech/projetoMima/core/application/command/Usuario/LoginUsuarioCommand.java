package school.sptech.projetoMima.core.application.command.Usuario;

public record LoginUsuarioCommand(
        String email,
        String senha
) {}
