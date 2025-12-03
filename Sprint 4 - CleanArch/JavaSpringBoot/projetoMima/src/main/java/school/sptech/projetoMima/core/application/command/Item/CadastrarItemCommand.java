package school.sptech.projetoMima.core.application.command.Item;

public class CadastrarItemCommand {
    public final String nome;
    public final Integer qtdEstoque;
    public final Double preco;
    public final Integer idCategoria;
    public final Integer idTamanho;
    public final Integer idCor;
    public final Integer idMaterial;
    public final Integer idFornecedor;

    public CadastrarItemCommand(String nome, Integer qtdEstoque, Double preco,
                                Integer idCategoria, Integer idTamanho, Integer idCor,
                                Integer idMaterial, Integer idFornecedor) {
        this.nome = nome;
        this.qtdEstoque = qtdEstoque;
        this.preco = preco;
        this.idCategoria = idCategoria;
        this.idTamanho = idTamanho;
        this.idCor = idCor;
        this.idMaterial = idMaterial;
        this.idFornecedor = idFornecedor;
    }
}
