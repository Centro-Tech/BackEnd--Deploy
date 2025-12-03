package school.sptech.projetoMima.infrastructure.di.Item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.projetoMima.core.adapter.Item.auxiliares.CategoriaGateway;
import school.sptech.projetoMima.core.application.usecase.Item.auxiliares.CategoriaUseCase.*;

@Configuration
public class CategoriaBeanConfig {

    @Bean
    public CriarCategoriaUseCase criarCategoriaUseCase(CategoriaGateway categoriaGateway) {
        return new CriarCategoriaUseCase(categoriaGateway);
    }

    @Bean
    public AtualizarCategoriaUseCase atualizarCategoriaUseCase(CategoriaGateway categoriaGateway) {
        return new AtualizarCategoriaUseCase(categoriaGateway);
    }

    @Bean
    public ListarCategoriasUseCase listarCategoriasUseCase(CategoriaGateway categoriaGateway) {
        return new ListarCategoriasUseCase(categoriaGateway);
    }

    @Bean
    public BuscarCategoriaPorIdUseCase buscarCategoriaPorIdUseCase(CategoriaGateway categoriaGateway) {
        return new BuscarCategoriaPorIdUseCase(categoriaGateway);
    }

    @Bean
    public DeletarCategoriaUseCase deletarCategoriaUseCase(CategoriaGateway categoriaGateway) {
        return new DeletarCategoriaUseCase(categoriaGateway);
    }
}
