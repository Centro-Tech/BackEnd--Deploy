package school.sptech.projetoMima.core.application.dto.usuarioDto;

import io.swagger.v3.oas.annotations.media.Schema;

public class SolicitarRecuperacaoSenhaDto {

    @Schema(description = "Email do usuário para recuperação de senha", example = "john@doe.com")
    private String email;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
