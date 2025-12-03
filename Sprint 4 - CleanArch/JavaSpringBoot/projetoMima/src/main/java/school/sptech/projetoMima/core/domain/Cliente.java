package school.sptech.projetoMima.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entidade que representa um cliente da loja.")
public class Cliente {

    @Schema(description = "Identificador único do cliente", example = "1")
    private Integer idCliente;

    @Schema(description = "Nome completo do cliente", example = "João da Silva")
    private String nome;

    @Schema(description = "Número de telefone do cliente", example = "(11) 91234-5678")
    private String telefone;

    @Schema(description = "CPF do cliente", example = "123.456.789-00")
    private String CPF;

    @Schema(description = "Endereço de e-mail do cliente", example = "joao.silva@email.com")
    private String email;

    @Schema(description = "Endereço residencial do cliente", example = "Rua das Flores, 123 - São Paulo/SP")
    private String endereco;

    public Integer getId() {
        return idCliente;
    }

    public void setId(Integer idCliente) {
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

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


    public Cliente get() {
        return this;
    }

    public Cliente orElseThrow(Object clienteNãoEncontrado) {
        return null;
    }
}
