package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Categoria.Entity;

import school.sptech.projetoMima.core.domain.item.Categoria;

public class CategoriaEntityMapper {

    public static CategoriaEntity toEntity(Categoria categoria) {
        if (categoria == null) return null;

        CategoriaEntity entity = new CategoriaEntity();
        entity.setId(categoria.getId());
        entity.setNome(categoria.getNome());
        return entity;
    }

    public static Categoria toDomain(CategoriaEntity entity) {
        if (entity == null) return null;

        Categoria categoria = new Categoria();
        categoria.setId(entity.getId());
        categoria.setNome(entity.getNome());
        return categoria;
    }
}
