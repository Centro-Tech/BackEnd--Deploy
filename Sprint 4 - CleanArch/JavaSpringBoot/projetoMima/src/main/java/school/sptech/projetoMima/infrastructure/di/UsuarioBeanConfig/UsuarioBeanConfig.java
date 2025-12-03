package school.sptech.projetoMima.infrastructure.di.UsuarioBeanConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.projetoMima.core.adapter.Usuario.AuthGateway;
import school.sptech.projetoMima.core.adapter.Usuario.CryptoGateway;
import school.sptech.projetoMima.core.adapter.Usuario.TokenGateway;
import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.adapter.Usuario.PasswordRecoveryPublisherGateway;
import school.sptech.projetoMima.core.application.usecase.Usuario.*;

import java.time.Duration;

@Configuration
public class UsuarioBeanConfig {

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase(UsuarioGateway gateway, CryptoGateway crypto) {
        return new CriarUsuarioUseCase(gateway, crypto);
    }

    @Bean
    public AtualizarUsuarioUseCase atualizarUsuarioUseCase(UsuarioGateway gateway) {
        return new AtualizarUsuarioUseCase(gateway);
    }

    @Bean
    public ListarUsuariosUseCase listarUsuariosUseCase(UsuarioGateway gateway) {
        return new ListarUsuariosUseCase(gateway);
    }

    @Bean
    public BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase(UsuarioGateway gateway) {
        return new BuscarUsuarioPorIdUseCase(gateway);
    }

    @Bean
    public ExcluirUsuarioUseCase excluirUsuarioUseCase(UsuarioGateway gateway) {
        return new ExcluirUsuarioUseCase(gateway);
    }

    @Bean
    public AutenticarUsuarioUseCase autenticarUsuarioUseCase(AuthGateway auth, TokenGateway token, UsuarioGateway gateway) {
        return new AutenticarUsuarioUseCase(auth, token, gateway);
    }

    @Bean
    public TrocarSenhaUseCase trocarSenhaUseCase(UsuarioGateway gateway, CryptoGateway crypto) {
        return new TrocarSenhaUseCase(gateway, crypto);
    }

    @Bean
    public SolicitarRecuperacaoSenhaUseCase solicitarRecuperacaoSenhaUseCase(UsuarioGateway gateway,
                                                                             PasswordRecoveryPublisherGateway publisherGateway,
                                                                             @Value("${app.password-recovery.token-validity-minutes:30}") long minutes) {
        return new SolicitarRecuperacaoSenhaUseCase(gateway, publisherGateway, Duration.ofMinutes(minutes));
    }

    @Bean
    public RedefinirSenhaUseCase redefinirSenhaUseCase(UsuarioGateway gateway, CryptoGateway crypto) {
        return new RedefinirSenhaUseCase(gateway, crypto);
    }

    @Bean
    public AutenticarPorTokenRecuperacaoUseCase autenticarPorTokenRecuperacaoUseCase(UsuarioGateway gateway, TokenGateway tokenGateway) {
        return new AutenticarPorTokenRecuperacaoUseCase(gateway, tokenGateway);
    }

    @Bean
    public RedefinirSenhaAutenticadoUseCase redefinirSenhaAutenticadoUseCase(UsuarioGateway gateway, CryptoGateway crypto) {
        return new RedefinirSenhaAutenticadoUseCase(gateway, crypto);
    }
}
