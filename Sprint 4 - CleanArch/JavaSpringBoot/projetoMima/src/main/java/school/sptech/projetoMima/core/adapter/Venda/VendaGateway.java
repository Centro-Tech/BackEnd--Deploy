package school.sptech.projetoMima.core.adapter.Venda;

import school.sptech.projetoMima.core.domain.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface VendaGateway {

    Venda save(Venda venda);

    boolean existsById (Integer id);

    void deleteById(Integer id);

    List<Venda> findAll();
    
    Page<Venda> findAll(Pageable pageable);

    Venda findById(Integer id);

    List<Venda> findByClienteId(Integer clienteId);

    List<Venda> findByUsuarioId(Integer usuarioId);

    List<Venda> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

    List<Venda> findByValorTotalBetween(Double valorMinimo, Double valorMaximo);
}
