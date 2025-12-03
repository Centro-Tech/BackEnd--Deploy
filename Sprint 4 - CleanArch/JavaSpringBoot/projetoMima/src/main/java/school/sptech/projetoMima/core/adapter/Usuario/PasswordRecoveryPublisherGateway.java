package school.sptech.projetoMima.core.adapter.Usuario;

import school.sptech.projetoMima.core.domain.Usuario;

public interface PasswordRecoveryPublisherGateway {
    void publishRecoveryInstructions(Usuario usuario, String token);
}

