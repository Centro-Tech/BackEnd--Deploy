package school.sptech.projetoMima.core.application.dto.itemDto.auxiliares;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import school.sptech.projetoMima.core.domain.item.Material;

@Schema(description = "DTO para material do item")
public class MaterialDto {

    @Schema(description = "Nome do material", example = "Algodão", required = true)
    @NotBlank(message = "O nome do material não pode estar em branco")
    @NotNull(message = "O nome do material é obrigatório")
    @Size(min = 2, message = "O nome do material deve ter pelo menos 2 caracteres")
    private String nome;

    public MaterialDto() {}

    public MaterialDto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static Material toEntity(MaterialDto dto) {
        Material entity = new Material();
        entity.setNome(dto.getNome());
        return entity;
    }
}
