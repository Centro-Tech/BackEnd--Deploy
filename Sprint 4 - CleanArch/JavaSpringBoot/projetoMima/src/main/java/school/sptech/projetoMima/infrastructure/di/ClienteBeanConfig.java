package school.sptech.projetoMima.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.projetoMima.core.adapter.Cliente.ClienteGateway;
import school.sptech.projetoMima.core.application.usecase.Cliente.*;

@Configuration
public class ClienteBeanConfig {

   @Bean
    public CadastrarClienteUseCase cadastrarClienteUseCase(ClienteGateway gateway) {
        return new CadastrarClienteUseCase(gateway);
    }

    @Bean
    public BuscarClientePorIdUseCase buscarClientePorIdUseCase(ClienteGateway gateway) {
        return new BuscarClientePorIdUseCase(gateway);
    }

    @Bean
    public AtualizarClienteUseCase atualizarClienteUseCase(ClienteGateway gateway) {
        return new AtualizarClienteUseCase(gateway);
    }

    @Bean
    public ListarClientesUseCase listarClientesUseCase(ClienteGateway gateway) {
        return new ListarClientesUseCase(gateway);
    }

    @Bean
    public ExcluirClienteUseCase excluirClienteUseCase(ClienteGateway gateway) {
        return new ExcluirClienteUseCase(gateway);
    }
}
