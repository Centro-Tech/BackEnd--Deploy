package school.sptech.projetoMima.core.application.dto.itemVendaDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ItemVendaRequestDto {

    @Schema(description = "ID do item a ser adicionado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer itemId;

    @Schema(description = "ID do cliente", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer clienteId;

    @Schema(description = "ID do funcionário", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer funcionarioId;

    @Schema(description = "Quantidade do item para vender", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Positive
    private Integer qtdParaVender;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Integer getQtdParaVender() {
        return qtdParaVender;
    }

    public void setQtdParaVender(Integer qtdParaVender) {
        this.qtdParaVender = qtdParaVender;
    }
}
