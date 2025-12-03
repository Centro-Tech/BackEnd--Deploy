package school.sptech.projetoMima.infrastructure.di.Item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.projetoMima.core.adapter.Item.auxiliares.MaterialGateway;
import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.application.usecase.Item.auxiliares.MaterialUseCase.*;

@Configuration
public class MaterialBeanConfig {

    @Bean
    public CriarMaterialUseCase criarMaterialUseCase(MaterialGateway materialGateway) {
        return new CriarMaterialUseCase(materialGateway);
    }

    @Bean
    public AtualizarMaterialUseCase atualizarMaterialUseCase(MaterialGateway materialGateway) {
        return new AtualizarMaterialUseCase(materialGateway);
    }

    @Bean
    public ListarMateriaisUseCase listarMateriaisUseCase(MaterialGateway materialGateway) {
        return new ListarMateriaisUseCase(materialGateway);
    }

    @Bean
    public BuscarMaterialPorIdUseCase buscarMaterialPorIdUseCase(MaterialGateway materialGateway) {
        return new BuscarMaterialPorIdUseCase(materialGateway);
    }

    @Bean
    public DeletarMaterialUseCase deletarMaterialUseCase(MaterialGateway materialGateway, ItemGateway itemGateway) {
        return new DeletarMaterialUseCase(materialGateway, itemGateway);
    }
}
