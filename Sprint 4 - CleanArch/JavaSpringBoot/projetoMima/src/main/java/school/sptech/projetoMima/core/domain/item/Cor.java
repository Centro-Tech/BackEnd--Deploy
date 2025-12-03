package school.sptech.projetoMima.core.domain.item;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Cor", description = "Representa a cor de um item")
public class Cor {
    @Schema(description = "Identificador da cor", example = "1")
    private Integer id;

    @Schema(description = "Nome da cor", example = "Azul")
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
