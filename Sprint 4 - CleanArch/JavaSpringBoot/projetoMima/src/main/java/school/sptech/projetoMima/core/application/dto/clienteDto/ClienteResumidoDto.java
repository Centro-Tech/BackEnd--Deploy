package school.sptech.projetoMima.core.application.dto.clienteDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representação resumida de um cliente")
public class ClienteResumidoDto {

    @Schema(description = "Identificador único do cliente", example = "1")
    private Integer idCliente;

    @Schema(description = "Nome completo do cliente", example = "Nome Completo")
    private String nome;

    @Schema(description = "Número de telefone do cliente", example = "(11) 91234-5678")
    private String telefone;

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
