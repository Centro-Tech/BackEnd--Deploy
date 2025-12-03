package school.sptech.projetoMima.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.projetoMima.core.adapter.ItemVenda.ItemVendaGateway;
import school.sptech.projetoMima.core.adapter.Cliente.ClienteGateway;
import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.application.usecase.ItemVenda.AdicionarItemAoCarrinhoUseCase;
import school.sptech.projetoMima.core.application.usecase.ItemVenda.ListarCarrinhoUseCase;
import school.sptech.projetoMima.core.application.usecase.ItemVenda.FinalizarCarrinhoUseCase;
import school.sptech.projetoMima.core.application.usecase.ItemVenda.RemoverUltimoItemDoCarrinhoUseCase;
import school.sptech.projetoMima.core.application.usecase.Venda.CriarVendaUseCase;
import school.sptech.projetoMima.infrastructure.persistance.ItemVendaPersistance.ItemVendaJpaAdapter;
import school.sptech.projetoMima.infrastructure.persistance.ItemVendaPersistance.ItemVendaJpaRepository;
import school.sptech.projetoMima.infrastructure.persistance.ItemVendaPersistance.ItemVendaEntityMapper;

@Configuration
public class ItemVendaBeanConfig {

    @Bean
    public ItemVendaEntityMapper itemVendaEntityMapper() {
        return new ItemVendaEntityMapper();
    }

    @Bean
    public ItemVendaGateway itemVendaGateway(ItemVendaJpaRepository itemVendaJpaRepository, ItemVendaEntityMapper itemVendaEntityMapper) {
        return new ItemVendaJpaAdapter(itemVendaJpaRepository, itemVendaEntityMapper);
    }

    @Bean
    public AdicionarItemAoCarrinhoUseCase adicionarItemAoCarrinhoUseCase(
            ItemVendaGateway itemVendaGateway,
            ItemGateway itemGateway,
            ClienteGateway clienteGateway,
            UsuarioGateway usuarioGateway) {
        return new AdicionarItemAoCarrinhoUseCase(itemVendaGateway, itemGateway, clienteGateway, usuarioGateway);
    }

    @Bean
    public ListarCarrinhoUseCase listarCarrinhoUseCase(ItemVendaGateway itemVendaGateway) {
        return new ListarCarrinhoUseCase(itemVendaGateway);
    }

    @Bean
    public FinalizarCarrinhoUseCase finalizarCarrinhoUseCase(ItemVendaGateway itemVendaGateway,
                                                             CriarVendaUseCase criarVendaUseCase,
                                                             ItemGateway itemGateway) {
        return new FinalizarCarrinhoUseCase(itemVendaGateway, criarVendaUseCase, itemGateway);
    }

    @Bean
    public RemoverUltimoItemDoCarrinhoUseCase removerUltimoItemDoCarrinhoUseCase(ItemVendaGateway itemVendaGateway) {
        return new RemoverUltimoItemDoCarrinhoUseCase(itemVendaGateway);
    }
}
