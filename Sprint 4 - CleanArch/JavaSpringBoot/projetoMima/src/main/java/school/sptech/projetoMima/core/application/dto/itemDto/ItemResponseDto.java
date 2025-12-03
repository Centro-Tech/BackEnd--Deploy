package school.sptech.projetoMima.core.application.dto.itemDto;

import school.sptech.projetoMima.core.application.dto.itemDto.auxiliares.*;
import school.sptech.projetoMima.core.application.dto.fornecedorDto.FornecedorResponseDto;

public class ItemResponseDto {
    private Integer id;
    private String nome;
    private Integer qtdEstoque;
    private String codigo;
    private Double preco;
    private TamanhoDto tamanho;
    private CorDto cor;
    private MaterialDto material;
    private CategoriaDto categoria;
    private FornecedorResponseDto fornecedor;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public TamanhoDto getTamanho() {
        return tamanho;
    }

    public void setTamanho(TamanhoDto tamanho) {
        this.tamanho = tamanho;
    }

    public CorDto getCor() {
        return cor;
    }

    public void setCor(CorDto cor) {
        this.cor = cor;
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public CategoriaDto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDto categoria) {
        this.categoria = categoria;
    }

    public FornecedorResponseDto getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorResponseDto fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
