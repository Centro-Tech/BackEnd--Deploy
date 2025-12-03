package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import school.sptech.projetoMima.infrastructure.persistance.FornecedorPersistance.FornecedorEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Categoria.Entity.CategoriaEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Cor.Entity.CorEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Material.Entity.MaterialEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Tamanho.Entity.TamanhoEntity;


@Entity
@Table(name = "item")
@Schema(description = "Entidade que representa um item no sistema")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do item gerado automaticamente pelo sistema", example = "1", type = "integer", format = "int32", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    @Schema(description = "Código único do item", example = "ITEM001", type = "string", maxLength = 50, requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigo;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    @Schema(description = "Quantidade em estoque do item", example = "100", type = "integer", format = "int32", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer qtdEstoque;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Nome do item", example = "Camiseta Básica", type = "string", maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkTamanho")
    @Schema(description = "Tamanho do item")
    private TamanhoEntity tamanho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkCor")
    @Schema(description = "Cor do item")
    private CorEntity cor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkMaterial")
    @Schema(description = "Material do item")
    private MaterialEntity material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkCategoria")
    @Schema(description = "Categoria do item")
    private CategoriaEntity categoria;

    @NotNull
    @Column(nullable = false)
    @Schema(description = "Preço do item", example = "29.99", type = "number", format = "decimal", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double preco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkFornecedor")
    @Schema(description = "Fornecedor do item")
    private FornecedorEntity fornecedor;

    public ItemEntity() {}

    public ItemEntity(Integer id, String codigo, Integer qtdEstoque, String nome, 
                     TamanhoEntity tamanho, CorEntity cor, MaterialEntity material, 
                     CategoriaEntity categoria, Double preco, FornecedorEntity fornecedor) {
        this.id = id;
        this.codigo = codigo;
        this.qtdEstoque = qtdEstoque;
        this.nome = nome;
        this.tamanho = tamanho;
        this.cor = cor;
        this.material = material;
        this.categoria = categoria;
        this.preco = preco;
        this.fornecedor = fornecedor;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(Integer qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TamanhoEntity getTamanho() {
        return tamanho;
    }

    public void setTamanho(TamanhoEntity tamanho) {
        this.tamanho = tamanho;
    }

    public CorEntity getCor() {
        return cor;
    }

    public void setCor(CorEntity cor) {
        this.cor = cor;
    }

    public MaterialEntity getMaterial() {
        return material;
    }

    public void setMaterial(MaterialEntity material) {
        this.material = material;
    }

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public FornecedorEntity getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorEntity fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", qtdEstoque=" + qtdEstoque +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}