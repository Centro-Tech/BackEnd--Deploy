package school.sptech.projetoMima.infrastructure.persistance.UsuarioPersistance;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import school.sptech.projetoMima.core.adapter.Usuario.AuthGateway;

@Component
public class SpringAuthAdapter implements AuthGateway {

    private final AuthenticationManager authenticationManager;

    public SpringAuthAdapter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Object authenticate(String email, String senha) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha)
        );
    }
}
