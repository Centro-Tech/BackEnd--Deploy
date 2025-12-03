package school.sptech.projetoMima.core.application.usecase.Usuario;

import school.sptech.projetoMima.core.adapter.Usuario.CryptoGateway;
import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.application.command.Usuario.CriarUsuarioCommand;
import school.sptech.projetoMima.core.domain.Usuario;

public class CriarUsuarioUseCase {

    private final UsuarioGateway gateway;
    private final CryptoGateway crypto;

    public CriarUsuarioUseCase(UsuarioGateway gateway, CryptoGateway crypto) {
        this.gateway = gateway;
        this.crypto = crypto;
    }

    public Usuario executar(CriarUsuarioCommand cmd, boolean usarSenhaPadrao) {
        String senha = usarSenhaPadrao ? "Mima@123" : cmd.senha();
        Usuario novo = new Usuario(
                cmd.nome(),
                cmd.email(),
                cmd.telefone(),
                cmd.endereco() == null ? "" : cmd.endereco(),
                crypto.encode(senha),
                cmd.cargo()
        );
        return gateway.save(novo);
    }

    public record Resultado(Usuario usuario, String senhaProvisoria) {}

    public Resultado executarComSenhaProvisoria(CriarUsuarioCommand cmd, boolean usarSenhaPadrao) {
        String senha = usarSenhaPadrao ? "Mima@123" : cmd.senha();
        Usuario novo = new Usuario(
                cmd.nome(),
                cmd.email(),
                cmd.telefone(),
                cmd.endereco() == null ? "" : cmd.endereco(),
                crypto.encode(senha),
                cmd.cargo()
        );
        Usuario salvo = gateway.save(novo);
        return new Resultado(salvo, usarSenhaPadrao ? senha : null);
    }
}
