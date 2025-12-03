package school.sptech.projetoMima.core.application.usecase.ItemVenda;

import school.sptech.projetoMima.core.adapter.ItemVenda.ItemVendaGateway;
import school.sptech.projetoMima.core.application.command.ItemVenda.RemoverUltimoItemDoCarrinhoCommand;
import school.sptech.projetoMima.core.application.exception.Venda.CarrinhoVazioException;
import school.sptech.projetoMima.core.domain.ItemVenda;
import school.sptech.projetoMima.core.domain.Pilha;

import java.util.List;

public class RemoverUltimoItemDoCarrinhoUseCase {

    private final ItemVendaGateway itemVendaGateway;

    public RemoverUltimoItemDoCarrinhoUseCase(ItemVendaGateway itemVendaGateway) {
        this.itemVendaGateway = itemVendaGateway;
    }

    public ItemVenda execute(RemoverUltimoItemDoCarrinhoCommand command) {
        // Busca TODOS os itens do carrinho (sem venda)
        List<ItemVenda> carrinho = itemVendaGateway.findByVendaIsNull();

        if (carrinho.isEmpty()) {
            throw new CarrinhoVazioException("O carrinho está vazio.");
        }

        Pilha<ItemVenda> pilhaCarrinho = new Pilha<>(carrinho.size());
        for (ItemVenda item : carrinho) {
            pilhaCarrinho.push(item);
        }

        ItemVenda ultimoItem = pilhaCarrinho.pop();
        itemVendaGateway.deleteById(ultimoItem.getId());

        return ultimoItem;
    }
}
