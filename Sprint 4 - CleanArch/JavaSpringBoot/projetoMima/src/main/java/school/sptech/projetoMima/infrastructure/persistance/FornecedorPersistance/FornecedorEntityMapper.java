package school.sptech.projetoMima.infrastructure.persistance.FornecedorPersistance;

import school.sptech.projetoMima.core.domain.Fornecedor;

public class FornecedorEntityMapper {
    public static FornecedorEntity toEntity(Fornecedor fornecedor) {
        FornecedorEntity entity = new FornecedorEntity();
        // Não definir o ID para permitir que o banco gere automaticamente
        if (fornecedor.getId() != null) {
            entity.setId(fornecedor.getId());
        }
        entity.setNome(fornecedor.getNome());
        entity.setTelefone(fornecedor.getTelefone());
        entity.setEmail(fornecedor.getEmail());
        return entity;
    }

    public static Fornecedor toDomain(FornecedorEntity entity) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(entity.getId());
        fornecedor.setNome(entity.getNome());
        fornecedor.setTelefone(entity.getTelefone());
        fornecedor.setEmail(entity.getEmail());
        return fornecedor;
    }
}
