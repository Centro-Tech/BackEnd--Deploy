package school.sptech.projetoMima.core.application.usecase.Venda;

import school.sptech.projetoMima.core.adapter.Venda.VendaGateway;
import school.sptech.projetoMima.core.adapter.ItemVenda.ItemVendaGateway;
import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.application.command.Venda.RemoverItemDaVendaComDtoCommand;
import school.sptech.projetoMima.core.domain.Venda;
import school.sptech.projetoMima.core.domain.ItemVenda;
import school.sptech.projetoMima.core.domain.item.Item;

public class RemoverItemDaVendaComDtoUseCase {

    private final VendaGateway vendaGateway;
    private final ItemVendaGateway itemVendaGateway;
    private final ItemGateway itemGateway;

    public RemoverItemDaVendaComDtoUseCase(VendaGateway vendaGateway, ItemVendaGateway itemVendaGateway, ItemGateway itemGateway) {
        this.vendaGateway = vendaGateway;
        this.itemVendaGateway = itemVendaGateway;
        this.itemGateway = itemGateway;
    }

    public Venda execute(RemoverItemDaVendaComDtoCommand command) {

        Venda venda = vendaGateway.findById(command.vendaId());
        if (venda == null) {
            throw new RuntimeException("Venda não encontrada");
        }

        ItemVenda itemVenda = itemVendaGateway.buscarPorIdEVenda(command.itemVendaId(), command.vendaId())
                .orElseThrow(() -> new RuntimeException("Este item não pertence à venda informada"));

        Item item = itemVenda.getItem();
        Double valorItem = item.getPreco() * itemVenda.getQtdParaVender();

        venda.setValorTotal(venda.getValorTotal() - valorItem);
        item.setQtdEstoque(item.getQtdEstoque() + itemVenda.getQtdParaVender());

        venda.getItensVenda().removeIf(i -> i.getId().equals(itemVenda.getId()));

        itemGateway.save(item);
        itemVendaGateway.deleteById(itemVenda.getId());

        return vendaGateway.save(venda);
    }
}
