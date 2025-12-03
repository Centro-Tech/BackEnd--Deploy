package school.sptech.projetoMima.core.application.dto.vendaDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import school.sptech.projetoMima.core.application.dto.itemVendaDto.ItemVendaResponseDto;
import school.sptech.projetoMima.core.domain.ItemVenda;

import java.time.LocalDateTime;
import java.util.List;

public class VendaResponseDto {
    private Integer id;
    private Integer clienteId;
    private Integer usuarioId;
    private Double valorTotal;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime data;
    
    private List<ItemVendaResponseDto> itensVenda;


    public VendaResponseDto() {
    }

    public VendaResponseDto(Integer id, Integer clienteId, Integer usuarioId, Double valorTotal, LocalDateTime data, List<ItemVendaResponseDto> itensVenda) {
        this.id = id;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
        this.valorTotal = valorTotal;
        this.data = data;
        this.itensVenda = itensVenda;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public List<ItemVendaResponseDto> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVendaResponseDto> itensVenda) {
        this.itensVenda = itensVenda;
    }
}