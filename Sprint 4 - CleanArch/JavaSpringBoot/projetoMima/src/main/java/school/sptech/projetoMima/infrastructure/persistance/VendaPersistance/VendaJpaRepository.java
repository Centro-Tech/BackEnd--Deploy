package school.sptech.projetoMima.infrastructure.persistance.VendaPersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.projetoMima.core.domain.Venda;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaJpaRepository extends JpaRepository<VendaEntity, Integer> {

    List<VendaEntity> findByCliente_IdCliente(Integer clienteId);

    List<VendaEntity> findByUsuario_Id(Integer usuarioId);

    List<VendaEntity> findByDataBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<VendaEntity> findByValorTotalBetween(Double valorMinimo, Double valorMaximo);
}
