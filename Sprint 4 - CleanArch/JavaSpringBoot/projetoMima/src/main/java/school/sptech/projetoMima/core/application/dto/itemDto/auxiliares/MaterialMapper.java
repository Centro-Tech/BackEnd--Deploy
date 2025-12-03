package school.sptech.projetoMima.core.application.dto.itemDto.auxiliares;

import school.sptech.projetoMima.core.domain.item.Material;

public class MaterialMapper {

    public static MaterialDto toDto(Material material) {
        if (material == null) return null;

        return new MaterialDto(material.getNome());
    }

    public static Material toDomain(MaterialDto dto) {
        if (dto == null) return null;

        Material material = new Material();
        material.setNome(dto.getNome());
        return material;
    }
}
