package school.sptech.projetoMima.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fornecedor {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do fornecedor gerado automaticamente pelo sistema", example = "1", type = "integer", format = "int32", required = true)
    private Integer id;

    @Schema(description = "Nome completo ou razão social do fornecedor", example = "Empresa XYZ LTDA", type = "string", maxLength = 100, required = true)
    private String nome;

    @Schema(description = "Número de telefone do fornecedor com DDD", example = "11987654321", type = "string", required = true)
    private String telefone;

    @Schema(description = "Endereço de e-mail para contato com o fornecedor", example = "contato@empresa.com", type = "string", format = "email", required = true)
    private String email;

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
}
