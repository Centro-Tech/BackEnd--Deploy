package school.sptech.projetoMima.core.application.usecase.Usuario;

import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.application.exception.Usuario.UsuarioNaoEncontradoException;
import school.sptech.projetoMima.core.domain.Usuario;

public class BuscarUsuarioPorIdUseCase {

    private final UsuarioGateway gateway;

    public BuscarUsuarioPorIdUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public Usuario executar(Integer id) {
        return gateway.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Funcionário com o ID " + id + " não encontrado!"));
    }
}
