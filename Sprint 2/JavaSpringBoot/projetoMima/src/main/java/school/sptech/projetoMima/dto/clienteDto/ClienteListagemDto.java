package school.sptech.projetoMima.dto.clienteDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representação para listagem de clientes")
public class ClienteListagemDto {

    @Schema(description = "Identificador único do cliente", example = "1")
    private Integer idCliente;

    @Schema(description = "Nome completo do cliente", example = "Nome Completo")
    private String nome;

    @Schema(description = "Endereço de e-mail do cliente", example = "email@dominio.com")
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
