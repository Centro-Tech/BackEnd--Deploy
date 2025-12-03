package school.sptech.projetoMima.dto.itemDto.auxiliares;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import school.sptech.projetoMima.entity.item.Tamanho;

@Schema(description = "DTO para tamanho do item")
public class TamanhoDto {

    @Schema(description = "Nome do tamanho do item", example = "M", required = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 5)
    private String nome;

    public TamanhoDto() {}

    public TamanhoDto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static Tamanho toEntity(TamanhoDto tamanhoDto) {
        Tamanho tamanho = new Tamanho();
        tamanho.setNome(tamanhoDto.getNome());
        return tamanho;
    }
}
