package school.sptech.projetoMima.core.application.dto.clienteDto;

import school.sptech.projetoMima.core.application.command.Cliente.CadastrarClienteCommand;
import school.sptech.projetoMima.core.domain.Cliente;

public class ClienteMapper {

    public static ClienteCadastroDto toCadastroDto(Cliente cliente) {
        ClienteCadastroDto dto = new ClienteCadastroDto();
        dto.setNome(cliente.getNome());
        dto.setTelefone(cliente.getTelefone());
        dto.setCPF(cliente.getCPF());
        dto.setEmail(cliente.getEmail());
        dto.setEndereco(cliente.getEndereco());
        return dto;
    }

    public static ClienteResumidoDto toResumidoDto(Cliente cliente) {
        ClienteResumidoDto dto = new ClienteResumidoDto();
        dto.setIdCliente(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setTelefone(cliente.getTelefone());
        return dto;
    }

    public static ClienteListagemDto toList(Cliente cliente) {
        ClienteListagemDto dto = new ClienteListagemDto();
        dto.setIdCliente(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        return dto;
    }

    public static Cliente toEntity(CadastrarClienteCommand dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setCPF(dto.CPF());
        cliente.setEmail(dto.email());
        cliente.setEndereco(dto.endereco());
        return cliente;
    }
}
