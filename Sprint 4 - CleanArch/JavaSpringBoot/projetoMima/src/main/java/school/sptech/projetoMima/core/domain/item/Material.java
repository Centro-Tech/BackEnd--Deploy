package school.sptech.projetoMima.core.domain.item;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Material", description = "Representa o material de um item")
public class Material {

    private Integer id;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {

        return id;

    }
    public void setId(Integer id) {
        this.id = id;
    }
}
