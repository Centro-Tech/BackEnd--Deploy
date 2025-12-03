package school.sptech.projetoMima.core.application.dto.itemDto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ItemVendaRequestDto {

    @NotNull
    @Valid
    private Integer vendaId;

    @NotNull
    @Valid
    private Integer itemId;

    @NotNull
    @Valid
    private Integer qtdParaVender;

    @Valid
    @NotNull
    private Integer clienteId;

    @Valid
    @NotNull
    private Integer funcionarioId;

    public Integer getVendaId() {
        return vendaId;
    }

    public void setVendaId(Integer vendaId) {
        this.vendaId = vendaId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQtdParaVender() {
        return qtdParaVender;
    }

    public void setQtdParaVender(Integer qtdParaVender) {
        this.qtdParaVender = qtdParaVender;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId (Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
}
