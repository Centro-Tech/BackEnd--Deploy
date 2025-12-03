package school.sptech.projetoMima.core.application.usecase.Usuario;

import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.application.exception.Usuario.UsuarioNaoEncontradoException;

public class ExcluirUsuarioUseCase {

    private final UsuarioGateway gateway;

    public ExcluirUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public void executar(Integer id) {
        if (!gateway.existsById(id)) {
            throw new UsuarioNaoEncontradoException("Funcionário não encontrado");
        }
        gateway.deleteById(id);
    }
}
