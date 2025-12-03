package school.sptech.projetoMima.core.application.dto.itemDto;

import school.sptech.projetoMima.core.application.dto.fornecedorDto.FornecedorMapper;
import school.sptech.projetoMima.core.domain.item.Item;
import school.sptech.projetoMima.core.application.dto.itemDto.auxiliares.*;

public class ItemMapper {

    public static ItemResponseDto toResponseDto(Item item) {
        if (item == null) return null;

        ItemResponseDto response = new ItemResponseDto();
        response.setCodigo(item.getCodigo());
        response.setNome(item.getNome());
        response.setQtdEstoque(item.getQtdEstoque());
        response.setPreco(item.getPreco());
        response.setId(item.getId());

        if (item.getTamanho() != null) {
            response.setTamanho(TamanhoMapper.toDto(item.getTamanho()));
        }
        if (item.getCor() != null) {
            response.setCor(CorMapper.toDto(item.getCor()));
        }
        if (item.getMaterial() != null) {
            response.setMaterial(MaterialMapper.toDto(item.getMaterial()));
        }
        if (item.getCategoria() != null) {
            response.setCategoria(CategoriaMapper.toDto(item.getCategoria()));
        }
        if (item.getFornecedor() != null) {
            response.setFornecedor(FornecedorMapper.toResponse(item.getFornecedor()));
        }

        return response;
    }

    public static ItemListDto toListDto(Item item) {
        if (item == null) return null;

        ItemListDto response = new ItemListDto();
        response.setCodigo(item.getCodigo());
        response.setPreco(item.getPreco());
        response.setQtdEstoque(item.getQtdEstoque());
        response.setNome(item.getNome());

        if (item.getTamanho() != null) {
            response.setTamanho(item.getTamanho().getNome());
        }
        if (item.getCor() != null) {
            response.setCor(item.getCor().getNome());
        }
        if (item.getCategoria() != null) {
            response.setCategoria(item.getCategoria().getNome());
        }

        return response;
    }
}
