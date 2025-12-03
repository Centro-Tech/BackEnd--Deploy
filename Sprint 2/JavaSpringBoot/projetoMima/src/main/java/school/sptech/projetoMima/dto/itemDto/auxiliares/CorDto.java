package school.sptech.projetoMima.dto.itemDto.auxiliares;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import school.sptech.projetoMima.entity.item.Cor;

@Schema(description = "DTO para cor do item")
public class CorDto {

    @Schema(description = "Nome da cor", example = "Azul", required = true)
    @NotBlank
    @NotNull
    @Size(min = 2)
    private String nome;

    public CorDto() {}

    public CorDto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static Cor toEntity(CorDto cor) {
        Cor response = new Cor();
        response.setNome(cor.getNome());
        return response;
    }
}
