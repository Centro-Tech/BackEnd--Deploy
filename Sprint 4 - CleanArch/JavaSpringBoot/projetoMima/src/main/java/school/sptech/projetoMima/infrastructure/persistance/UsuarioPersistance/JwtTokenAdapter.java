package school.sptech.projetoMima.infrastructure.persistance.UsuarioPersistance;

import org.springframework.stereotype.Component;
import school.sptech.projetoMima.core.adapter.Usuario.TokenGateway;
import school.sptech.projetoMima.infrastructure.config.GerenciadorTokenJwt;

@Component
public class JwtTokenAdapter implements TokenGateway {

    private final GerenciadorTokenJwt jwt;

    public JwtTokenAdapter(GerenciadorTokenJwt jwt) {
        this.jwt = jwt;
    }

    @Override
    public String generate(Object authentication) {
        return jwt.generateToken((org.springframework.security.core.Authentication) authentication);
    }
}
