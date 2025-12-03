package school.sptech.projetoMima.dto.itemDto;

import school.sptech.projetoMima.dto.fornecedorDto.FornecedorMapper;
import school.sptech.projetoMima.entity.Fornecedor;
import school.sptech.projetoMima.entity.item.*;

public class ItemMapper {

    public static ItemResponseDto toResponse(Item item) {
        ItemResponseDto response = new ItemResponseDto();

        response.setCodigo(item.getCodigo());
        response.setNome(item.getNome());
        response.setQtdEstoque(item.getQtdEstoque());

        return response;
    }

    public static ItemListDto toList(Item item) {
        ItemListDto response = new ItemListDto();

        response.setCodigo(item.getCodigo());
        response.setPreco(item.getPreco());
        response.setQtdEstoque(item.getQtdEstoque());
        response.setNome(item.getNome());
        response.setTamanho(item.getTamanho());
        response.setFornecedor(FornecedorMapper.toResponse(item.getFornecedor()));

        return response;
    }

    public static Item toEntity(ItemRequestDto request) {
        Item item = new Item();

        item.setNome(request.getNome());
        item.setQtdEstoque(request.getQtdEstoque());
        item.setPreco(request.getPreco());

        Tamanho tamanho = new Tamanho();
        tamanho.setId(request.getIdTamanho());
        tamanho.setNome(request.getNome());
        item.setTamanho(tamanho);

        Cor cor = new Cor();
        cor.setId(request.getIdCor());
        cor.setNome(request.getNome());
        item.setCor(cor);

        Material material = new Material();
        material.setId(request.getIdMaterial());
        material.setNome(request.getNome());
        item.setMaterial(material);

        Categoria categoria = new Categoria();
        categoria.setId(request.getIdCategoria());
        categoria.setNome(request.getNome());
        item.setCategoria(categoria);

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(request.getIdFornecedor());
        fornecedor.setNome(request.getNome());
        item.setFornecedor(fornecedor);

        return item;
    }


    public static Item fromResponseToEntity(ItemResponseDto request) {
        Item response = new Item();

        response.setCodigo(request.getCodigo());
        response.setNome(request.getNome());
        response.setQtdEstoque(request.getQtdEstoque());

        return response;
    }

    public static Item fromListToEntity(ItemListDto request) {
        Item response = new Item();

        response.setCodigo(request.getCodigo());
        response.setNome(request.getNome());
        response.setTamanho(request.getTamanho());
        response.setQtdEstoque(request.getQtdEstoque());
        response.setPreco(request.getPreco());

        return response;
    }
}
