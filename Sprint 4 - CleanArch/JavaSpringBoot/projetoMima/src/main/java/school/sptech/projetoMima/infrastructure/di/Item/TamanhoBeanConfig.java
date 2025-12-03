package school.sptech.projetoMima.infrastructure.di.Item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.projetoMima.core.adapter.Item.auxiliares.TamanhoGateway;
import school.sptech.projetoMima.core.application.usecase.Item.auxiliares.TamanhoUseCase.*;

@Configuration
public class TamanhoBeanConfig {

    @Bean
    public CriarTamanhoUseCase criarTamanhoUseCase(TamanhoGateway tamanhoGateway) {
        return new CriarTamanhoUseCase(tamanhoGateway);
    }

    @Bean
    public AtualizarTamanhoUseCase atualizarTamanhoUseCase(TamanhoGateway tamanhoGateway) {
        return new AtualizarTamanhoUseCase(tamanhoGateway);
    }

    @Bean
    public ListarTamanhosUseCase listarTamanhosUseCase(TamanhoGateway tamanhoGateway) {
        return new ListarTamanhosUseCase(tamanhoGateway);
    }

    @Bean
    public BuscarTamanhoPorIdUseCase buscarTamanhoPorIdUseCase(TamanhoGateway tamanhoGateway) {
        return new BuscarTamanhoPorIdUseCase(tamanhoGateway);
    }

    @Bean
    public DeletarTamanhoUseCase deletarTamanhoUseCase(TamanhoGateway tamanhoGateway) {
        return new DeletarTamanhoUseCase(tamanhoGateway);
    }
}
