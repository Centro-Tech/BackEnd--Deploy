package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Cor.Entity;

import school.sptech.projetoMima.core.domain.item.Cor;

public class CorEntityMapper {

    public static CorEntity toEntity(Cor cor) {
        if (cor == null) return null;

        CorEntity entity = new CorEntity();
        entity.setId(cor.getId());
        entity.setNome(cor.getNome());
        return entity;
    }

    public static Cor toDomain(CorEntity entity) {
        if (entity == null) return null;

        Cor cor = new Cor();
        cor.setId(entity.getId());
        cor.setNome(entity.getNome());
        return cor;
    }
}
