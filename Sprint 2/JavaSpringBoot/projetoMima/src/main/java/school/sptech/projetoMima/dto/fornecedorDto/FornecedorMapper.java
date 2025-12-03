package school.sptech.projetoMima.dto.fornecedorDto;

import school.sptech.projetoMima.entity.Fornecedor;

public class FornecedorMapper {

    public static FornecedorResponseDto toResponse(Fornecedor fornecedor) {
        FornecedorResponseDto response = new FornecedorResponseDto();

        response.setId(fornecedor.getId());
        response.setNome(fornecedor.getNome());
        response.setTelefone(fornecedor.getTelefone());
        response.setEmail(fornecedor.getEmail());

        return response;
    }

    public static Fornecedor toEntity(FornecedorRequestDto fornecedor) {
        Fornecedor response = new Fornecedor();

        response.setNome(fornecedor.getNome());
        response.setEmail(fornecedor.getEmail());
        response.setTelefone(fornecedor.getTelefone());

        return response;
    }





}
