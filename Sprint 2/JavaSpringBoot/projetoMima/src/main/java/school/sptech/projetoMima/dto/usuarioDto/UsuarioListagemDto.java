package school.sptech.projetoMima.dto.usuarioDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioListagemDto {

    @NotBlank @NotNull @Size(max = 100, min = 1)
    private String nome;

    @NotBlank @NotNull @Size(max = 100, min = 1) @Email
    private String email;

    @NotBlank @NotNull @Size(max = 50, min = 2)
    private String cargo;

    public @NotBlank @NotNull @Size(max = 100, min = 1) String getNome() {
        return nome;
    }

    public void setNome(@NotBlank @NotNull @Size(max = 100, min = 1) String nome) {
        this.nome = nome;
    }

    public @NotBlank @NotNull @Size(max = 100, min = 1) @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @NotNull @Size(max = 100, min = 1) @Email String email) {
        this.email = email;
    }

    public @NotBlank @NotNull @Size(max = 50, min = 2) String getCargo() {
        return cargo;
    }

    public void setCargo(@NotBlank @NotNull @Size(max = 50, min = 2) String cargo) {
        this.cargo = cargo;
    }
}
