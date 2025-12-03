package school.sptech.projetoMima.core.domain.item;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entidade que representa uma categoria de produto.")
public class Categoria {

    @Schema(description = "Identificador único da catogoria", example = "1")
    private Integer id;

    @Schema(description = "Nome completo da categoria", example = "CAMISETA")
    private String nome;

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
}
