package school.sptech.projetoMima.core.application.dto.usuarioDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioCadastroDto {

    private Integer id;

    @NotBlank @NotNull @Size(min = 3, max = 100)
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotBlank @NotNull @Size(min = 8, max = 20)
    private String telefone;

    @NotBlank @NotNull @Size(min = 5, max = 100) @Email
    private String email;

    @NotBlank @NotNull @Size(min = 2, max = 50)
    private String cargo;

    @NotBlank @NotNull @Size(min = 2, max = 100)
    private String endereco;

    @NotBlank @NotNull @Size(min = 6, max = 20)
    private String senha;

    private String imagem;

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public @NotBlank @NotNull @Size(min = 3, max = 100) String getNome() {
        return nome;
    }

    public void setNome(@NotBlank @NotNull @Size(min = 3, max = 100) String nome) {
        this.nome = nome;
    }

    public @NotBlank @NotNull @Size(min = 8, max = 20) String getTelefone() {
        return telefone;
    }

    public void setTelefone(@NotBlank @NotNull @Size(min = 8, max = 20) String telefone) {
        this.telefone = telefone;
    }

    public @NotBlank @NotNull @Size(min = 5, max = 100) @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @NotNull @Size(min = 5, max = 100) @Email String email) {
        this.email = email;
    }

    public @NotBlank @NotNull @Size(min = 2, max = 50) String getCargo() {
        return cargo;
    }

    public void setCargo(@NotBlank @NotNull @Size(min = 2, max = 50) String cargo) {
        this.cargo = cargo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
