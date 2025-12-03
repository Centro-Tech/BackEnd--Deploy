package school.sptech.projetoMima.infrastructure.persistance.ClientePersistance.Enitity;

import school.sptech.projetoMima.core.domain.Cliente;

public class ClienteEntityMapper {

    public static ClienteEntity ofDomain(Cliente cliente) {
        return new ClienteEntity(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getCPF(),
                cliente.getEmail(),
                cliente.getEndereco()
        );
    }

    public static Cliente toDomain(ClienteEntity entity) {
        Cliente cliente = new Cliente();
        cliente.setId(entity.getIdCliente());
        cliente.setNome(entity.getNome());
        cliente.setTelefone(entity.getTelefone());
        cliente.setCPF(entity.getCPF());
        cliente.setEmail(entity.getEmail());
        cliente.setEndereco(entity.getEndereco());
        return cliente;
    }
}
