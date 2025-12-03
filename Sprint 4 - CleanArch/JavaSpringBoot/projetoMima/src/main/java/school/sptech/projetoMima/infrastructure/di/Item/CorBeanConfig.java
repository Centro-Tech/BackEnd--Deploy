package school.sptech.projetoMima.infrastructure.di.Item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.projetoMima.core.adapter.Item.auxiliares.CorGateway;
import school.sptech.projetoMima.core.application.usecase.Item.auxiliares.CorUseCase.*;

@Configuration
public class CorBeanConfig {

    @Bean
    public CriarCorUseCase criarCorUseCase(CorGateway corGateway) {
        return new CriarCorUseCase(corGateway);
    }

    @Bean
    public AtualizarCorUseCase atualizarCorUseCase(CorGateway corGateway) {
        return new AtualizarCorUseCase(corGateway);
    }

    @Bean
    public ListarCoresUseCase listarCoresUseCase(CorGateway corGateway) {
        return new ListarCoresUseCase(corGateway);
    }

    @Bean
    public BuscarCorPorIdUseCase buscarCorPorIdUseCase(CorGateway corGateway) {
        return new BuscarCorPorIdUseCase(corGateway);
    }

    @Bean
    public DeletarCorUseCase deletarCorUseCase(CorGateway corGateway) {
        return new DeletarCorUseCase(corGateway);
    }
}
