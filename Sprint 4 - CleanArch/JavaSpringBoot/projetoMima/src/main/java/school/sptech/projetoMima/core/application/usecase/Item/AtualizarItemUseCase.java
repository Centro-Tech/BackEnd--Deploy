package school.sptech.projetoMima.core.application.usecase.Item;

import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.adapter.Item.auxiliares.*;
import school.sptech.projetoMima.core.adapter.Fornecedor.FornecedorGateway;
import school.sptech.projetoMima.core.application.command.Item.AtualizarItemCommand;
import school.sptech.projetoMima.core.application.exception.Item.ItemNaoEncontradoException;
import school.sptech.projetoMima.core.application.exception.Item.ItemInvalidoException;
import school.sptech.projetoMima.core.domain.item.Item;

public class AtualizarItemUseCase {

    private final ItemGateway itemGateway;
    private final TamanhoGateway tamanhoGateway;
    private final CorGateway corGateway;
    private final MaterialGateway materialGateway;
    private final CategoriaGateway categoriaGateway;
    private final FornecedorGateway fornecedorGateway;

    public AtualizarItemUseCase(ItemGateway itemGateway,
                                TamanhoGateway tamanhoGateway,
                                CorGateway corGateway,
                                MaterialGateway materialGateway,
                                CategoriaGateway categoriaGateway,
                                FornecedorGateway fornecedorGateway) {
        this.itemGateway = itemGateway;
        this.tamanhoGateway = tamanhoGateway;
        this.corGateway = corGateway;
        this.materialGateway = materialGateway;
        this.categoriaGateway = categoriaGateway;
        this.fornecedorGateway = fornecedorGateway;
    }

    public Item execute(AtualizarItemCommand command) {
        if (!itemGateway.existsById(command.id())) {
            throw new ItemNaoEncontradoException("Item com id " + command.id() + " não encontrado");
        }

        Item item = itemGateway.findById(command.id());

        if (command.nome() != null) item.setNome(command.nome());
        if (command.qtdEstoque() != null) item.setQtdEstoque(command.qtdEstoque());
        if (command.preco() != null) item.setPreco(command.preco());

        if (command.idCategoria() != null) {
            item.setCategoria(categoriaGateway.findById(command.idCategoria()));
        }
        if (command.idTamanho() != null) {
            item.setTamanho(tamanhoGateway.findById(command.idTamanho()));
        }
        if (command.idCor() != null) {
            item.setCor(corGateway.findById(command.idCor()));
        }
        if (command.idMaterial() != null) {
            item.setMaterial(materialGateway.findById(command.idMaterial()));
        }
        if (command.idFornecedor() != null) {
            item.setFornecedor(fornecedorGateway.findById(command.idFornecedor()));
        }

        validarCampos(item);
        return itemGateway.save(item);
    }

    private void validarCampos(Item item) {
        if (item.getNome() == null || item.getNome().isBlank() ||
            item.getQtdEstoque() == null || item.getQtdEstoque() <= 0 ||
            item.getPreco() == null || item.getPreco() <= 0) {
            throw new ItemInvalidoException("Campos obrigatórios inválidos ou vazios");
        }
    }
}
