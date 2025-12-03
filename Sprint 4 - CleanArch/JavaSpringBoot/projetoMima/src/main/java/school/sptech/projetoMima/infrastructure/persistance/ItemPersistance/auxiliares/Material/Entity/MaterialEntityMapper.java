package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Material.Entity;

import school.sptech.projetoMima.core.domain.item.Material;

public class MaterialEntityMapper {

    public static MaterialEntity toEntity(Material material) {
        if (material == null) return null;

        MaterialEntity entity = new MaterialEntity();
        entity.setId(material.getId());
        entity.setNome(material.getNome());
        return entity;
    }

    public static Material toDomain(MaterialEntity entity) {
        if (entity == null) return null;

        Material material = new Material();
        material.setId(entity.getId());
        material.setNome(entity.getNome());
        return material;
    }
}
