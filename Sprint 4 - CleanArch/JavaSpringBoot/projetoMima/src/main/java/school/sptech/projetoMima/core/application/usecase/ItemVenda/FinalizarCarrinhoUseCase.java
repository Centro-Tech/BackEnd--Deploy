package school.sptech.projetoMima.core.application.usecase.ItemVenda;

import school.sptech.projetoMima.core.adapter.ItemVenda.ItemVendaGateway;
import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.application.command.ItemVenda.FinalizarCarrinhoCommand;
import school.sptech.projetoMima.core.application.exception.Venda.CarrinhoVazioException;
import school.sptech.projetoMima.core.application.usecase.Venda.CriarVendaUseCase;
import school.sptech.projetoMima.core.application.command.Venda.CriarVendaCommand;
import school.sptech.projetoMima.core.domain.ItemVenda;
import school.sptech.projetoMima.core.domain.Venda;
import school.sptech.projetoMima.core.domain.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FinalizarCarrinhoUseCase {

    private final ItemVendaGateway itemVendaGateway;
    private final CriarVendaUseCase criarVendaUseCase;
    private final ItemGateway itemGateway;

    public FinalizarCarrinhoUseCase(ItemVendaGateway itemVendaGateway, CriarVendaUseCase criarVendaUseCase, ItemGateway itemGateway) {
        this.itemVendaGateway = itemVendaGateway;
        this.criarVendaUseCase = criarVendaUseCase;
        this.itemGateway = itemGateway;
    }

    public Venda execute(FinalizarCarrinhoCommand command) {
        List<ItemVenda> carrinho = itemVendaGateway.findByVendaIsNull();

        if (carrinho.isEmpty()) {
            throw new CarrinhoVazioException("Carrinho está vazio.");
        }

        // Calcula o valor total da venda somando os itens
        double valorTotal = carrinho.stream()
                .mapToDouble(item -> item.getItem().getPreco() * item.getQtdParaVender())
                .sum();

        // Monta o comando para criar a venda
        CriarVendaCommand criarCommand = new CriarVendaCommand(
                valorTotal,
                command.clienteId(),
                carrinho.stream()
                        .map(item -> item.getItem().getId())
                        .collect(Collectors.toList()),
                command.funcionarioId()
        );

        // Cria a venda
        Venda novaVenda = criarVendaUseCase.execute(criarCommand);

        // Associa todos os itens do carrinho à nova venda
        // E ATUALIZA O ESTOQUE
        List<ItemVenda> itensSalvos = new ArrayList<>();
        for (ItemVenda itemVenda : carrinho) {
            itemVenda.setVenda(novaVenda);
            ItemVenda saved = itemVendaGateway.save(itemVenda);
            itensSalvos.add(saved);

            // Atualizar estoque - diminui a quantidade vendida
            Item item = itemVenda.getItem();
            int qtdAtual = item.getQtdEstoque();
            int qtdVendida = itemVenda.getQtdParaVender();

            if (qtdAtual < qtdVendida) {
                throw new RuntimeException("Estoque insuficiente para o item: " + item.getNome() +
                    ". Disponível: " + qtdAtual + ", Solicitado: " + qtdVendida);
            }

            item.setQtdEstoque(qtdAtual - qtdVendida);
            itemGateway.save(item);
        }

        // Agora populamos a venda com os itens salvos para que o retorno contenha os IDs
        novaVenda.setItensVenda(itensSalvos);

        return novaVenda;
    }
}
