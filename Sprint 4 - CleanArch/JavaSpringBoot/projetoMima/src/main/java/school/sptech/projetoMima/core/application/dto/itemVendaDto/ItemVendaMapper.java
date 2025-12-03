package school.sptech.projetoMima.core.application.dto.itemVendaDto;

import school.sptech.projetoMima.core.domain.ItemVenda;

public class ItemVendaMapper {

    public static ItemVendaResponseDto toResponseDto(ItemVenda entity) {
        if (entity == null) return null;

        ItemVendaResponseDto dto = new ItemVendaResponseDto();
        dto.setId(entity.getId());
        dto.setQtdParaVender(entity.getQtdParaVender());
        dto.setItemId(entity.getItem() != null ? entity.getItem().getId() : null);

        if (entity.getItem() != null) {
            dto.setNomeItem(entity.getItem().getNome());
        }

        return dto;
    }

    public static ItemVenda toEntity(ItemVendaRequestDto dto) {
        if (dto == null) return null;

        ItemVenda entity = new ItemVenda();
        entity.setQtdParaVender(dto.getQtdParaVender());
        return entity;
    }
}
