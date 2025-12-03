package school.sptech.projetoMima.core.application.command.Usuario;

public record TrocarSenhaCommand(
        String emailAutenticado,
        String senhaAtual,
        String novaSenha
) {}