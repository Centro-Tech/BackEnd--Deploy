package school.sptech.projetoMima.core.application.dto.clienteDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para o cadastro de um novo cliente")
public class ClienteCadastroDto {

    @Schema(description = "Nome completo do cliente", example = "Nome Completo")
    private String nome;

    @Schema(description = "Número de telefone do cliente", example = "(11) 91234-5678")
    private String telefone;

    @Schema(description = "CPF do cliente", example = "123.456.789-00")
    private String CPF;

    @Schema(description = "Endereço de e-mail do cliente", example = "email@dominio.com")
    private String email;

    @Schema(description = "Endereço residencial do cliente", example = "Rua das Flores, 123 - São Paulo/SP")
    private String endereco;

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
}
