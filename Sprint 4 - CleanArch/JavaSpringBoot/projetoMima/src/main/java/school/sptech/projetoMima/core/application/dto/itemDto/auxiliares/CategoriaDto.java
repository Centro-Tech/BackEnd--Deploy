package school.sptech.projetoMima.core.application.dto.itemDto.auxiliares;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import school.sptech.projetoMima.core.domain.item.Categoria;

@Schema(description = "DTO para categoria do item")
public class CategoriaDto {

    @Schema(description = "Tipo da categoria", example = "Casual", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    @Size(min = 2)
    private String tipo;

    public CategoriaDto() {}

    public CategoriaDto(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public static Categoria toEntity(CategoriaDto categoria) {
        Categoria response = new Categoria();
        response.setNome(categoria.getTipo());
        return response;
    }
}
