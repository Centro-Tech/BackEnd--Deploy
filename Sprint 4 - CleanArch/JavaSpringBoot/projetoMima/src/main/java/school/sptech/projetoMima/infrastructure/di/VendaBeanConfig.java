package school.sptech.projetoMima.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.projetoMima.core.adapter.Venda.VendaGateway;
import school.sptech.projetoMima.core.adapter.Venda.ComprovanteVendaPublisherGateway;
import school.sptech.projetoMima.core.adapter.Cliente.ClienteGateway;
import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.adapter.ItemVenda.ItemVendaGateway;
import school.sptech.projetoMima.core.adapter.Usuario.UsuarioGateway;
import school.sptech.projetoMima.core.application.usecase.Venda.*;
import school.sptech.projetoMima.infrastructure.persistance.VendaPersistance.VendaJpaAdapter;
import school.sptech.projetoMima.infrastructure.persistance.VendaPersistance.VendaJpaRepository;
import school.sptech.projetoMima.infrastructure.persistance.VendaPersistance.VendaEntityMapper;

@Configuration
public class VendaBeanConfig {

    @Bean
    public VendaEntityMapper vendaEntityMapper() {
        return new VendaEntityMapper();
    }

    @Bean
    public VendaGateway vendaGateway(VendaJpaRepository vendaJpaRepository, VendaEntityMapper vendaEntityMapper) {
        return new VendaJpaAdapter(vendaJpaRepository, vendaEntityMapper);
    }

    @Bean
    public CriarVendaUseCase criarVendaUseCase(VendaGateway vendaGateway, ClienteGateway clienteGateway, UsuarioGateway usuarioGateway) {
        return new CriarVendaUseCase(vendaGateway, clienteGateway, usuarioGateway);
    }

    @Bean
    public ListarVendasUseCase listarVendasUseCase(VendaGateway vendaGateway) {
        return new ListarVendasUseCase(vendaGateway);
    }

    @Bean
    public FiltrarVendasPorDataUseCase filtrarVendasPorDataUseCase(VendaGateway vendaGateway) {
        return new FiltrarVendasPorDataUseCase(vendaGateway);
    }

    @Bean
    public FiltrarVendasPorClienteUseCase filtrarVendasPorClienteUseCase(VendaGateway vendaGateway) {
        return new FiltrarVendasPorClienteUseCase(vendaGateway);
    }

    @Bean
    public FiltrarVendasPorValorUseCase filtrarVendasPorValorUseCase(VendaGateway vendaGateway) {
        return new FiltrarVendasPorValorUseCase(vendaGateway);
    }

    @Bean
    public RemoverItemDaVendaUseCase removerItemDaVendaUseCase(VendaGateway vendaGateway, ItemVendaGateway itemVendaGateway) {
        return new RemoverItemDaVendaUseCase(vendaGateway, itemVendaGateway);
    }

    @Bean
    public RemoverItemDaVendaComDtoUseCase removerItemDaVendaComDtoUseCase(VendaGateway vendaGateway,
                                                                         ItemVendaGateway itemVendaGateway,
                                                                         ItemGateway itemGateway) {
        return new RemoverItemDaVendaComDtoUseCase(vendaGateway, itemVendaGateway, itemGateway);
    }

    @Bean
    public EnviarComprovanteVendaUseCase enviarComprovanteVendaUseCase(ComprovanteVendaPublisherGateway comprovantePublisherGateway) {
        return new EnviarComprovanteVendaUseCase(comprovantePublisherGateway);
    }

    @Bean
    public FinalizarVendaUseCase finalizarVendaUseCase(VendaGateway vendaGateway, 
                                                       ComprovanteVendaPublisherGateway comprovantePublisherGateway) {
        return new FinalizarVendaUseCase(vendaGateway, comprovantePublisherGateway);
    }
}
