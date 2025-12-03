package school.sptech.projetoMima.core.application.usecase.Usuario;

import school.sptech.projetoMima.core.adapter.Usuario.CryptoGateway;
import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.application.exception.Usuario.UsuarioNaoEncontradoException;
import school.sptech.projetoMima.core.domain.Usuario;

/**
 * Redefine a senha de um usuário autenticado via JWT (sem exigir senha atual).
 * Usado quando a redefinição ocorre sem token de recuperação.
 */
public class RedefinirSenhaAutenticadoUseCase {

    private final UsuarioGateway usuarioGateway;
    private final CryptoGateway cryptoGateway;

    public RedefinirSenhaAutenticadoUseCase(UsuarioGateway usuarioGateway, CryptoGateway cryptoGateway) {
        this.usuarioGateway = usuarioGateway;
        this.cryptoGateway = cryptoGateway;
    }

    public void executar(String email, String novaSenha) {
        Usuario usuario = usuarioGateway.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        usuario.setSenha(cryptoGateway.encode(novaSenha));
        // Limpa eventual token de recuperação antigo
        usuario.setRecoveryToken(null);
        usuario.setRecoveryTokenExpiry(null);
        usuarioGateway.save(usuario);
    }
}

