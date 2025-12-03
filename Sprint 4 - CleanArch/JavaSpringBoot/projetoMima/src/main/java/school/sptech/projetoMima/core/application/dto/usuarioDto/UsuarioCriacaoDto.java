package school.sptech.projetoMima.core.application.dto.usuarioDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UsuarioCriacaoDto {

    private Integer id;

    @Size(min = 3, max = 100)
    @Schema(description = "Nome de usuário", example = "Rózin")
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Email
    @Schema(description = "Email de Usuário", example = "rozin@gmail.com")
    private String email;

    @Size(min = 5, max = 100)
    @Schema(description = "Senha do usuário", example = "******")
    private String senha;

    @Size(min = 5, max = 100)
    @Schema(description = "Senha do usuário", example = "******")
    private String telefone;

    @Size(min = 5, max = 100)
    @Schema(description = "Senha do usuário", example = "******")
    private String cargo;

    @Size(min = 5, max = 100)
    @Schema(description = "Senha do usuário", example = "******")
    private String endereco;

    private String imagem;

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
