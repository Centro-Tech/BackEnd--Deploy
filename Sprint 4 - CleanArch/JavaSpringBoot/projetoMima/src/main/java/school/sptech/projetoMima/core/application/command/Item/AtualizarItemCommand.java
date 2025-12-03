package school.sptech.projetoMima.core.application.command.Item;

public record AtualizarItemCommand(
        Integer id,
        String nome,
        Integer qtdEstoque,
        Double preco,
        Integer idCategoria,
        Integer idTamanho,
        Integer idCor,
        Integer idMaterial,
        Integer idFornecedor
) { }
