package school.sptech.projetoMima.core.application.usecase.Usuario;

import school.sptech.projetoMima.core.adapter.Usuario.PasswordRecoveryPublisherGateway;
import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.domain.Usuario;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class SolicitarRecuperacaoSenhaUseCase {

    private final UsuarioGateway usuarioGateway;
    private final PasswordRecoveryPublisherGateway publisherGateway;
    private final Duration tokenValidity;

    public SolicitarRecuperacaoSenhaUseCase(UsuarioGateway usuarioGateway,
                                            PasswordRecoveryPublisherGateway publisherGateway,
                                            Duration tokenValidity) {
        this.usuarioGateway = usuarioGateway;
        this.publisherGateway = publisherGateway;
        this.tokenValidity = tokenValidity;
    }

    public void executar(String email) {
        Usuario usuario = usuarioGateway.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email não encontrado"));
        String token = UUID.randomUUID().toString().replace("-", "");
        usuario.setRecoveryToken(token);
        usuario.setRecoveryTokenExpiry(LocalDateTime.now().plus(tokenValidity));
        usuarioGateway.save(usuario);
        publisherGateway.publishRecoveryInstructions(usuario, token);
    }
}

