package school.sptech.projetoMima.core.application.usecase.Usuario;

import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.application.command.Usuario.AtualizarUsuarioCommand;
import school.sptech.projetoMima.core.application.exception.Usuario.UsuarioNaoEncontradoException;
import school.sptech.projetoMima.core.domain.Usuario;

public class AtualizarUsuarioUseCase {
    private final UsuarioGateway gateway;

    public AtualizarUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public Usuario executar(AtualizarUsuarioCommand cmd) {
        Usuario existente = gateway.findById(cmd.id())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Funcionário com o ID " + cmd.id() + " não encontrado!"));

        existente.setNome(cmd.nome());
        existente.setTelefone(cmd.telefone());
        existente.setEmail(cmd.email());
        existente.setCargo(cmd.cargo());
        existente.setEndereco(cmd.endereco());
        existente.setImagem(cmd.imagem());

        return gateway.save(existente);
    }
}
