package school.sptech.projetoMima.core.application.dto.vendaDto;

import school.sptech.projetoMima.core.application.dto.itemVendaDto.ItemVendaResponseDto;
import school.sptech.projetoMima.core.domain.ItemVenda;
import school.sptech.projetoMima.core.domain.Venda;

import java.util.List;
import java.util.stream.Collectors;

public class VendaMapper {

    public static Venda toEntity(VendaRequestDto request) {
        Venda venda = new Venda();
        venda.setValorTotal(request.getValorTotal());
        return venda;
    }

    public static VendaResponseDto toResponseDto(Venda venda) {
        VendaResponseDto response = new VendaResponseDto();
        response.setId(venda.getId());
        response.setValorTotal(venda.getValorTotal());
        response.setData(venda.getData());

        if (venda.getCliente() != null) {
            response.setClienteId(venda.getCliente().getId());
        }

        if (venda.getUsuario() != null) {
            response.setUsuarioId(venda.getUsuario().getId());
        }

        if (venda.getItensVenda() != null) {
            List<ItemVendaResponseDto> itensDto = venda.getItensVenda()
                    .stream()
                    .map(VendaMapper::toItemVendaResponse)
                    .collect(Collectors.toList());

            response.setItensVenda(itensDto);
        }

        return response;
    }

    private static ItemVendaResponseDto toItemVendaResponse(ItemVenda itemVenda) {
        ItemVendaResponseDto dto = new ItemVendaResponseDto();

        dto.setId(itemVenda.getId());
        dto.setQtdParaVender(itemVenda.getQtdParaVender());

        if (itemVenda.getItem() != null) {
            dto.setItemId(itemVenda.getItem().getId());
            dto.setNomeItem(itemVenda.getItem().getNome()); // ou getDescricao, depende da sua entidade
        }

        return dto;
    }
}
