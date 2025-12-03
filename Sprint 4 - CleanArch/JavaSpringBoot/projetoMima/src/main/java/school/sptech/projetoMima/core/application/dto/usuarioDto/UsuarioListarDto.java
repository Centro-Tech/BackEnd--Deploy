package school.sptech.projetoMima.core.application.dto.usuarioDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UsuarioListarDto {

    @Schema(description = "Id do usuário")
    private Integer id;

    @Size(min = 3, max = 100)
    @Schema(description = "Nome de usuário", example = "Rózin")
    private String nome;

    @Email
    @Schema(description = "Email de Usuário", example = "rozin@gmail.com")
    private String email;

    @Size(min = 5, max = 100)
    @Schema(description = "Senha do usuário", example = "******")
    private String senha;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
