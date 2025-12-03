package school.sptech.projetoMima.infrastructure.persistance.ClientePersistance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.projetoMima.core.adapter.Cliente.ClienteGateway;
import school.sptech.projetoMima.core.domain.Cliente;
import school.sptech.projetoMima.infrastructure.persistance.ClientePersistance.Enitity.ClienteEntity;
import school.sptech.projetoMima.infrastructure.persistance.ClientePersistance.Enitity.ClienteEntityMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClienteJpaAdapter implements ClienteGateway {

    private final ClienteJpaRepository repository;

    public ClienteJpaAdapter(ClienteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = ClienteEntityMapper.ofDomain(cliente);
        ClienteEntity saved = repository.save(entity);
        return ClienteEntityMapper.toDomain(saved);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return repository.existsByCPF(cpf);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Cliente> findAll() {
        return repository.findAll().stream()
                .map(ClienteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ClienteEntityMapper::toDomain);
    }

    @Override
    public Cliente findById(Integer id) {
        Optional<ClienteEntity> entity = repository.findById(id);
        return entity.map(ClienteEntityMapper::toDomain).orElse(null);
    }
}
