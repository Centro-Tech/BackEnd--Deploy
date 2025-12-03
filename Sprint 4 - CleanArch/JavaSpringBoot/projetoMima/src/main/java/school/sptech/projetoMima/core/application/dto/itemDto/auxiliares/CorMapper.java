package school.sptech.projetoMima.core.application.dto.itemDto.auxiliares;

import school.sptech.projetoMima.core.domain.item.Cor;

public class CorMapper {

    public static CorDto toDto(Cor cor) {
        if (cor == null) return null;

        return new CorDto(cor.getNome());
    }

    public static Cor toDomain(CorDto dto) {
        if (dto == null) return null;

        Cor cor = new Cor();
        cor.setNome(dto.getNome());
        return cor;
    }
}
