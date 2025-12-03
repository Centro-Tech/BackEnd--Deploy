package school.sptech.projetoMima.core.domain.item;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.projetoMima.core.domain.Fornecedor;

@Schema(description = "Entidade que representa os vestuários disponíveis na loja.")
public class Item {

    public Item(Integer id, String codigo, Integer qtdEstoque, String nome, Tamanho tamanho, Cor cor, Material material, Categoria categoria, Double preco, Fornecedor fornecedor) {
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

    public Item() {
    }

    @Schema(description = "Identificador único do item", example = "1")
    private Integer id;

    @Schema(description = "Código de identificação único do item", example = "BLU123M")
    private String codigo;

    @Schema(description = "Quantidade disponível em estoque", example = "10")
    private Integer qtdEstoque;

    @Schema(description = "Nome do item", example = "Camiseta Polo Azul")
    private String nome;

    @Schema(description = "Tamanho do item", example = "M")
    private Tamanho tamanho;

    @Schema(description = "Cor do item", example = "Azul")
    private Cor cor;

    @Schema(description = "Material do item", example = "Poliamida")
    private Material material;

    @Schema(description = "Tipo do item", example = "Camiseta")
    private Categoria categoria;

    @Schema(description = "Preço do item", example = "59.99")
    private Double preco;

    @Schema(description = "Fornecedor responsável por este item")
    private Fornecedor fornecedor;

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

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }



}
