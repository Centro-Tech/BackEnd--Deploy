package school.sptech.projetoMima.core.application.dto.vendaDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

public class VendaRequestDto {

    @Schema(description = "Valor total da venda", example = "199.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @PositiveOrZero
    private Double valorTotal;

    @Schema(description = "ID do cliente que está realizando a compra", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer clienteId;

    @Schema(description = "Lista de IDs dos itens que compõem a venda", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty
    private List<Integer> itensVenda;

    @Schema(description = "ID do funcionário responsável pela venda", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer funcionarioId;

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

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
}
