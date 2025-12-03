package school.sptech.projetoMima.core.application.dto.fornecedorDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class FornecedorRequestDto {

    @NotBlank
    @Size(max = 100, min = 1)
    @Schema(description = "Nome do fornecedor", example = "Empresa XYZ LTDA", minLength = 1, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @NotBlank
    @Size(min = 8, max = 11)
    @Pattern(regexp = "\\d+", message = "Telefone deve conter apenas números")
    @Schema(description = "Telefone do fornecedor com 11 dígitos, somente números", example = "11987654321", minLength = 11, maxLength = 11, requiredMode = Schema.RequiredMode.REQUIRED)
    private String telefone;

    @NotBlank
    @Size(max = 100, min = 1)
    @Email
    @Schema(description = "Email do fornecedor", example = "contato@empresa.com", minLength = 1, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    public FornecedorRequestDto() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
