package school.sptech.projetoMima.core.application.dto.itemDto.auxiliares;

import school.sptech.projetoMima.core.domain.item.Categoria;

public class CategoriaMapper {

    public static CategoriaDto toDto(Categoria categoria) {
        if (categoria == null) return null;

        return new CategoriaDto(categoria.getNome());
    }

    public static Categoria toDomain(CategoriaDto dto) {
        if (dto == null) return null;

        Categoria categoria = new Categoria();
        categoria.setNome(dto.getTipo());
        return categoria;
    }
}
