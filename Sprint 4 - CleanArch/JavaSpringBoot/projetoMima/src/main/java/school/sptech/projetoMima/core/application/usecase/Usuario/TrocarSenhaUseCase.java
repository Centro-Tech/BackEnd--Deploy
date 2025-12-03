package school.sptech.projetoMima.core.application.usecase.Usuario;

import school.sptech.projetoMima.core.adapter.Usuario.CryptoGateway;
import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.application.command.Usuario.TrocarSenhaCommand;
import school.sptech.projetoMima.core.application.exception.Usuario.UsuarioNaoEncontradoException;
import school.sptech.projetoMima.core.domain.Usuario;

public class TrocarSenhaUseCase {

    private final UsuarioGateway gateway;
    private final CryptoGateway crypto;

    public TrocarSenhaUseCase(UsuarioGateway gateway, CryptoGateway crypto) {
        this.gateway = gateway;
        this.crypto = crypto;
    }

    public void executar(TrocarSenhaCommand cmd) {
        Usuario usuario = gateway.findByEmail(cmd.emailAutenticado())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o e-mail: " + cmd.emailAutenticado()));

        if (!crypto.matches(cmd.senhaAtual(), usuario.getSenha())) {
            throw new RuntimeException("Senha atual incorreta.");
        }

        usuario.setSenha(crypto.encode(cmd.novaSenha()));
        gateway.save(usuario);
    }
}
