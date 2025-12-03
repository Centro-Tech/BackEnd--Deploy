package school.sptech.projetoMima.infrastructure.persistance.UsuarioPersistance;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import school.sptech.projetoMima.core.adapter.Usuario.CryptoGateway;

@Component
public class SpringCryptoAdapter implements CryptoGateway {

    private final PasswordEncoder encoder;

    public SpringCryptoAdapter(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode(String raw) {
        return encoder.encode(raw);
    }

    @Override
    public boolean matches(String raw, String encoded) {
        return encoder.matches(raw, encoded);
    }
}
