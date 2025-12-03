package school.sptech.projetoMima.core.application.usecase.Usuario;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import school.sptech.projetoMima.core.adapter.Usuario.TokenGateway;
import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.application.exception.Usuario.UsuarioNaoEncontradoException;
import school.sptech.projetoMima.core.domain.Usuario;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * Use case responsável por validar um token de recuperação de senha e gerar um JWT
 * permitindo que o usuário acesse endpoints autenticados para redefinição de senha.
 */
public class AutenticarPorTokenRecuperacaoUseCase {

    private final UsuarioGateway usuarioGateway;
    private final TokenGateway tokenGateway;

    public AutenticarPorTokenRecuperacaoUseCase(UsuarioGateway usuarioGateway, TokenGateway tokenGateway) {
        this.usuarioGateway = usuarioGateway;
        this.tokenGateway = tokenGateway;
    }

    public record Resultado(Usuario usuario, String token) {}

    public Resultado executar(String recoveryToken) {
        if (recoveryToken == null || recoveryToken.isBlank()) {
            throw new IllegalArgumentException("Token de recuperação é obrigatório");
        }

        Usuario usuario = usuarioGateway.findByRecoveryToken(recoveryToken)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Token inválido ou expirado"));

        if (usuario.getRecoveryTokenExpiry() == null || usuario.getRecoveryTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expirado");
        }

        // Cria uma autenticação mínima só com o email do usuário. Sem authorities (coleção vazia)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha(), Collections.emptyList());

        String jwt = tokenGateway.generate(authenticationToken);
        return new Resultado(usuario, jwt);
    }
}

