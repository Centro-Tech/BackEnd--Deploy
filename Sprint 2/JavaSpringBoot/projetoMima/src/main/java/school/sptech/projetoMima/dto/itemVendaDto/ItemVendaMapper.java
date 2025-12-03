package school.sptech.projetoMima.dto.itemVendaDto;

import school.sptech.projetoMima.entity.Cliente;
import school.sptech.projetoMima.entity.ItemVenda;
import school.sptech.projetoMima.entity.Usuario;
import school.sptech.projetoMima.entity.item.Item;

public class ItemVendaMapper {

    public static ItemVendaResponseDto toResponse(ItemVenda entity) {
        if (entity == null) return null;

        ItemVendaResponseDto dto = new ItemVendaResponseDto();
        dto.setId(entity.getId());
        dto.setNomeItem(entity.getItem().getNome());
        dto.setQtdParaVender(entity.getQtdParaVender());
        return dto;
    }

    public static ItemVenda toEntity(ItemVendaRequestDto dto, Item item, Cliente cliente, Usuario funcionario) {
        ItemVenda entity = new ItemVenda();
        entity.setItem(item);
        entity.setCliente(cliente);
        entity.setFuncionario(funcionario);
        entity.setQtdParaVender(dto.getQtdParaVender());
        entity.setFornecedor(item.getFornecedor());
        return entity;
    }
}

