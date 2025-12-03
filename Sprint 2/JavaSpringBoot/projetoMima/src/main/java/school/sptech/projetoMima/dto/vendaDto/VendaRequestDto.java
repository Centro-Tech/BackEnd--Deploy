package school.sptech.projetoMima.dto.vendaDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

public class VendaRequestDto {

    @Schema(description = "Valor total da venda", example = "199.99", required = true)
    @NotNull
    @PositiveOrZero
    private Double valorTotal;

    @Schema(description = "ID do cliente que está realizando a compra", required = true)
    @NotNull
    private Integer clienteId;

    @Schema(description = "Lista de IDs dos itens que compõem a venda", required = true)
    @NotEmpty
    private List<Integer> itensVenda;

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public List<Integer> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<Integer> itensVenda) {
        this.itensVenda = itensVenda;
    }
}
