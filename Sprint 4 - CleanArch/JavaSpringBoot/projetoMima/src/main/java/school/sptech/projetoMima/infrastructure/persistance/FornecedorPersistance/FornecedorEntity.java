package school.sptech.projetoMima.infrastructure.persistance.FornecedorPersistance;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "fornecedor")
public class FornecedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do fornecedor gerado automaticamente pelo sistema", example = "1", type = "integer", format = "int32", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @NotNull
    @NotBlank
    @Schema(description = "Nome completo ou razão social do fornecedor", example = "Empresa XYZ LTDA", type = "string", maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @Schema(description = "Número de telefone do fornecedor com DDD", example = "11987654321", type = "string")
    private String telefone;

    @Schema(description = "Endereço de e-mail para contato com o fornecedor", example = "contato@empresa.com", type = "string", format = "email")
    private String email;

    @Schema(description = "Endereço físico do fornecedor", example = "Rua das Indústrias, 1000", type = "string")
    private String endereco;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
