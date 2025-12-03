package school.sptech.projetoMima.dto.itemDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import school.sptech.projetoMima.entity.Fornecedor;

public class ItemRequestDto {

    @NotBlank
    @NotNull
    @Size(max = 100, min = 1)
    @Schema(description = "Nome do item", example = "Camiseta", maxLength = 100, minLength = 1, required = true)
    private String nome;

    @NotNull
    @DecimalMin(value = "1.0")
    @Schema(description = "Quantidade disponível no estoque", example = "50", minimum = "1", required = true)
    private Integer qtdEstoque;

    @NotNull
    @DecimalMin(value = "1.0")
    @Schema(description = "Preço do item", example = "79.90", minimum = "1.0", required = true)
    private Double preco;

    @NotNull
    @Schema(description = "ID do tamanho do item", example = "1", required = true)
    private Integer idTamanho;

    @NotNull
    @Schema(description = "ID da cor do item", example = "1", required = true)
    private Integer idCor;

    @NotNull
    @Schema(description = "ID do material do item", example = "1", required = true)
    private Integer idMaterial;

    @NotNull
    @Schema(description = "ID da categoria do item", example = "1", required = true)
    private Integer idCategoria;

    @NotNull
    @Schema(description = "Objeto fornecedor com os dados básicos", example = "{\"id\": 1, \"nome\": \"Empresa XYZ LTDA\", \"telefone\": \"11987654321\", \"email\": \"contato@empresa.com\"}", required = true)
    private Integer idFornecedor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(Integer qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getIdTamanho() {
        return idTamanho;
    }

    public void setIdTamanho(Integer idTamanho) {
        this.idTamanho = idTamanho;
    }

    public Integer getIdCor() {
        return idCor;
    }

    public void setIdCor(Integer idCor) {
        this.idCor = idCor;
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
}
