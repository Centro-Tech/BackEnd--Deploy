package school.sptech.projetoMima.core.adapter.Cliente;

import school.sptech.projetoMima.core.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteGateway {
    Cliente save(Cliente cliente);
    boolean existsByCpf(String cpf);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    List<Cliente> findAll();
    
    Page<Cliente> findAll(Pageable pageable);

    Cliente findById(Integer id);
}
