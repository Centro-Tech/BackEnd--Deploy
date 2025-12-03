package school.sptech.projetoMima.core.application.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import school.sptech.projetoMima.core.application.dto.usuarioDto.UsuarioDetalhesDto;
import school.sptech.projetoMima.core.domain.Usuario;
import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioGateway usuarioGateway;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioGateway.findByEmail(username);
        if (usuarioOpt.isEmpty()) {

            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
        }
        Usuario usuario = usuarioOpt.get();
        return new UsuarioDetalhesDto(usuario);
    }
}
