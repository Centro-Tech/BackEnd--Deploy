package school.sptech.projetoMima.core.application.usecase.Venda;

import school.sptech.projetoMima.core.adapter.Venda.VendaGateway;
import school.sptech.projetoMima.core.adapter.ItemVenda.ItemVendaGateway;
import school.sptech.projetoMima.core.application.command.Venda.RemoverItemDaVendaCommand;
import school.sptech.projetoMima.core.domain.Venda;
import school.sptech.projetoMima.core.domain.ItemVenda;

public class RemoverItemDaVendaUseCase {

    private final VendaGateway vendaGateway;
    private final ItemVendaGateway itemVendaGateway;

    public RemoverItemDaVendaUseCase(VendaGateway vendaGateway, ItemVendaGateway itemVendaGateway) {
        this.vendaGateway = vendaGateway;
        this.itemVendaGateway = itemVendaGateway;
    }

    public void execute(RemoverItemDaVendaCommand command) {

        Venda venda = vendaGateway.findById(command.vendaId());
        if (venda == null) {
            throw new RuntimeException("Venda não encontrada");
        }

        ItemVenda itemVenda = itemVendaGateway.buscarPorIdEVenda(command.itemId(), command.vendaId())
                .orElseThrow(() -> new RuntimeException("Item não pertence à venda informada"));

        itemVendaGateway.deleteById(itemVenda.getId());

        double valorItem = itemVenda.getItem().getPreco() * itemVenda.getQtdParaVender();
        venda.setValorTotal(venda.getValorTotal() - valorItem);
        vendaGateway.save(venda);
    }
}
