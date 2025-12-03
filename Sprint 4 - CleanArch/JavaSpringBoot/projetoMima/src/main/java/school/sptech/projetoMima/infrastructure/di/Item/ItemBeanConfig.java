package school.sptech.projetoMima.infrastructure.di.Item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.projetoMima.core.adapter.Fornecedor.FornecedorGateway;
import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.adapter.Item.auxiliares.*;
import school.sptech.projetoMima.core.application.usecase.Item.*;
import school.sptech.projetoMima.core.application.usecase.Item.auxiliares.AdicionarEstoqueItemUseCase;

@Configuration
public class ItemBeanConfig {

    @Bean
    public CadastrarItemUseCase cadastrarItemUseCase(
            ItemGateway itemGateway,
            TamanhoGateway tamanhoGateway,
            CorGateway corGateway,
            MaterialGateway materialGateway,
            CategoriaGateway categoriaGateway,
            FornecedorGateway fornecedorGateway) {
        return new CadastrarItemUseCase(itemGateway, tamanhoGateway, corGateway,
                                      materialGateway, categoriaGateway, fornecedorGateway);
    }

    @Bean
    public AtualizarItemUseCase atualizarItemUseCase(
            ItemGateway itemGateway,
            TamanhoGateway tamanhoGateway,
            CorGateway corGateway,
            MaterialGateway materialGateway,
            CategoriaGateway categoriaGateway,
            FornecedorGateway fornecedorGateway) {
        return new AtualizarItemUseCase(itemGateway, tamanhoGateway, corGateway,
                                      materialGateway, categoriaGateway, fornecedorGateway);
    }

    @Bean
    public BuscarItemPorIdUseCase buscarItemPorIdUseCase(ItemGateway itemGateway) {
        return new BuscarItemPorIdUseCase(itemGateway);
    }

    @Bean
    public BuscarItemPorCodigoUseCase buscarItemPorCodigoUseCase(ItemGateway itemGateway) {
        return new BuscarItemPorCodigoUseCase(itemGateway);
    }

    @Bean
    public ExcluirItemUseCase excluirItemUseCase(ItemGateway itemGateway) {
        return new ExcluirItemUseCase(itemGateway);
    }

    @Bean
    public DeletarItemPorCodigoUseCase deletarItemPorCodigoUseCase(ItemGateway itemGateway) {
        return new DeletarItemPorCodigoUseCase(itemGateway);
    }

    @Bean
    public ListarItensUseCase listarItensUseCase(ItemGateway itemGateway) {
        return new ListarItensUseCase(itemGateway);
    }

    @Bean
    public ListarEstoqueUseCase listarEstoqueUseCase(ItemGateway itemGateway) {
        return new ListarEstoqueUseCase(itemGateway);
    }

    @Bean
    public FiltrarItemPorCategoriaUseCase filtrarItemPorCategoriaUseCase(ItemGateway itemGateway) {
        return new FiltrarItemPorCategoriaUseCase(itemGateway);
    }

    @Bean
    public FiltrarItemPorFornecedorUseCase filtrarItemPorFornecedorUseCase(ItemGateway itemGateway) {
        return new FiltrarItemPorFornecedorUseCase(itemGateway);
    }

    @Bean
    public FiltrarItemPorNomeUseCase filtrarItemPorNomeUseCase(ItemGateway itemGateway) {
        return new FiltrarItemPorNomeUseCase(itemGateway);
    }

    @Bean
    public AdicionarEstoqueItemUseCase adicionarEstoqueItemUseCase(ItemGateway itemGateway) {
        return new AdicionarEstoqueItemUseCase(itemGateway);
    }
}
