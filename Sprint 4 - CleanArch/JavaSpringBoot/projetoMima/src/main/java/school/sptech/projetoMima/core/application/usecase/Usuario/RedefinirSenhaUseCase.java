package school.sptech.projetoMima.core.application.usecase.Usuario;

import school.sptech.projetoMima.core.adapter.Usuario.CryptoGateway;
import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.application.exception.Usuario.UsuarioNaoEncontradoException;
import school.sptech.projetoMima.core.domain.Usuario;

import java.time.LocalDateTime;

public class RedefinirSenhaUseCase {

    private final UsuarioGateway usuarioGateway;
    private final CryptoGateway cryptoGateway;

    public RedefinirSenhaUseCase(UsuarioGateway usuarioGateway, CryptoGateway cryptoGateway) {
        this.usuarioGateway = usuarioGateway;
        this.cryptoGateway = cryptoGateway;
    }

    public void executar(String token, String novaSenha) {
        // Buscar usuário pelo token de recuperação
        Usuario usuario = usuarioGateway.findByRecoveryToken(token)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Token inválido ou expirado"));

        // Verificar se o token não expirou
        if (usuario.getRecoveryTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expirado");
        }

        // Criptografar nova senha
        String novaSenhaCriptografada = cryptoGateway.encode(novaSenha);

        // Atualizar senha e limpar token de recuperação
        usuario.setSenha(novaSenhaCriptografada);
        usuario.setRecoveryToken(null);
        usuario.setRecoveryTokenExpiry(null);

        // Salvar usuário
        usuarioGateway.save(usuario);
    }
}
