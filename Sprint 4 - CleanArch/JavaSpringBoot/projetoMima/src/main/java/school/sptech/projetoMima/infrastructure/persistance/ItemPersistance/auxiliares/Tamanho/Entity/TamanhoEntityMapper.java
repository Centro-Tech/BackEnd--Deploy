package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Tamanho.Entity;

import school.sptech.projetoMima.core.domain.item.Tamanho;

public class TamanhoEntityMapper {

    public static TamanhoEntity toEntity(Tamanho tamanho) {
        if (tamanho == null) return null;

        TamanhoEntity entity = new TamanhoEntity();
        entity.setId(tamanho.getId());
        entity.setNome(tamanho.getNome());
        return entity;
    }

    public static Tamanho toDomain(TamanhoEntity entity) {
        if (entity == null) return null;

        Tamanho tamanho = new Tamanho();
        tamanho.setId(entity.getId());
        tamanho.setNome(entity.getNome());
        return tamanho;
    }
}
