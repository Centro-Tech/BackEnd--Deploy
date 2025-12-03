package school.sptech.projetoMima.core.application.usecase.Item;

import school.sptech.projetoMima.core.adapter.Fornecedor.FornecedorGateway;
import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.application.command.Item.CadastrarItemCommand;
import school.sptech.projetoMima.core.application.exception.Item.ItemInvalidoException;
import school.sptech.projetoMima.core.adapter.Item.auxiliares.*;
import school.sptech.projetoMima.core.domain.item.Item;
import school.sptech.projetoMima.core.domain.Fornecedor;

import java.util.Random;

public class CadastrarItemUseCase {

    private final ItemGateway itemGateway;
    private final TamanhoGateway tamanhoGateway;
    private final CorGateway corGateway;
    private final MaterialGateway materialGateway;
    private final CategoriaGateway categoriaGateway;
    private final FornecedorGateway fornecedorGateway;

    public CadastrarItemUseCase(ItemGateway itemGateway,
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

    public Item execute(CadastrarItemCommand command) {

        Item item = new Item();
        item.setNome(command.nome);
        item.setQtdEstoque(command.qtdEstoque);
        item.setPreco(command.preco);

        item.setTamanho(tamanhoGateway.findById(command.idTamanho));
        item.setCor(corGateway.findById(command.idCor));
        item.setMaterial(materialGateway.findById(command.idMaterial));
        item.setCategoria(categoriaGateway.findById(command.idCategoria));
        item.setFornecedor(fornecedorGateway.findById(command.idFornecedor));

        validarCampos(item);

        String codigo = gerarCodigo(item);
        item.setCodigo(codigo);

        return itemGateway.save(item);
    }

    private void validarCampos(Item item) {
        if (item.getNome() == null || item.getNome().isBlank()) {
            throw new ItemInvalidoException("Nome do item é obrigatório");
        }

        if (item.getQtdEstoque() == null || item.getQtdEstoque() <= 0) {
            throw new ItemInvalidoException("Quantidade em estoque deve ser maior que zero");
        }

        if (item.getPreco() == null || item.getPreco() <= 0) {
            throw new ItemInvalidoException("Preço deve ser maior que zero");
        }

        if (item.getCategoria() == null || item.getCategoria().getNome() == null || item.getCategoria().getNome().isBlank()) {
            throw new ItemInvalidoException("Categoria é obrigatória");
        }

        if (item.getTamanho() == null || item.getTamanho().getNome() == null || item.getTamanho().getNome().isBlank()) {
            throw new ItemInvalidoException("Tamanho é obrigatório");
        }

        if (item.getCor() == null || item.getCor().getNome() == null || item.getCor().getNome().isBlank()) {
            throw new ItemInvalidoException("Cor é obrigatória");
        }

        if (item.getMaterial() == null || item.getMaterial().getNome() == null || item.getMaterial().getNome().isBlank()) {
            throw new ItemInvalidoException("Material é obrigatório");
        }

        if (item.getFornecedor() == null) {
            throw new ItemInvalidoException("Fornecedor é obrigatório");
        }
    }

    private String gerarCodigo(Item item) {
        String categoriaNome = item.getCategoria().getNome().toUpperCase();
        String tamanhoNome = item.getTamanho().getNome().toUpperCase();

        String codigoIdentificacao;

        if (categoriaNome.contains("BLUSA")) codigoIdentificacao = "BL";
        else if (categoriaNome.contains("CAMISETA")) codigoIdentificacao = "BL";
        else if (categoriaNome.contains("VESTIDO")) codigoIdentificacao = "VE";
        else codigoIdentificacao = "XX";

        int numeroAleatorio = 1000000 + new Random().nextInt(9000000);

        return codigoIdentificacao + numeroAleatorio + tamanhoNome;
    }
}
