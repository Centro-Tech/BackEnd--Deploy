package school.sptech.projetoMima.core.application.usecase.Usuario;

import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.application.exception.Usuario.UsuarioListaVaziaException;
import school.sptech.projetoMima.core.domain.Usuario;

import java.util.List;

public class ListarUsuariosUseCase {

    private final UsuarioGateway gateway;

    public ListarUsuariosUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public List<Usuario> executar() {
        List<Usuario> lista = gateway.findAll();
        if (lista.isEmpty()) {
            throw new UsuarioListaVaziaException("Lista de funcionários está vazia");
        }
        return lista;
    }
}
