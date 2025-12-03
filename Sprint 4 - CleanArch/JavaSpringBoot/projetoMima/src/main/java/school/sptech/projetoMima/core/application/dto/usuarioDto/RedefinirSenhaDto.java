package school.sptech.projetoMima.core.application.dto.usuarioDto;

import io.swagger.v3.oas.annotations.media.Schema;

public class RedefinirSenhaDto {

    @Schema(description = "Token recebido por e-mail para redefinir senha", example = "123e4567-e89b-12d3-a456-426614174000")
    private String token;

    @Schema(description = "Nova senha do usuário", example = "novaSenha123")
    private String novaSenha;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getNovaSenha() { return novaSenha; }
    public void setNovaSenha(String novaSenha) { this.novaSenha = novaSenha; }
}
