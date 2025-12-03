package school.sptech.projetoMima.core.application.dto.itemDto.auxiliares;

import school.sptech.projetoMima.core.domain.item.Tamanho;

public class TamanhoMapper {

    public static TamanhoDto toDto(Tamanho tamanho) {
        if (tamanho == null) return null;

        return new TamanhoDto(tamanho.getNome());
    }

    public static Tamanho toDomain(TamanhoDto dto) {
        if (dto == null) return null;

        Tamanho tamanho = new Tamanho();
        tamanho.setNome(dto.getNome());
        return tamanho;
    }
}
