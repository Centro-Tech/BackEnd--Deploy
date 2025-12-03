package school.sptech.projetoMima.infrastructure.persistance.VendaPersistance;

import school.sptech.projetoMima.core.adapter.Venda.VendaGateway;
import school.sptech.projetoMima.core.domain.Venda;
import school.sptech.projetoMima.infrastructure.persistance.VendaPersistance.VendaEntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendaJpaAdapter implements VendaGateway {

    private final VendaJpaRepository vendaJpaRepository;
    private final VendaEntityMapper vendaEntityMapper;

    public VendaJpaAdapter(VendaJpaRepository vendaJpaRepository, VendaEntityMapper vendaEntityMapper) {
        this.vendaJpaRepository = vendaJpaRepository;
        this.vendaEntityMapper = vendaEntityMapper;
    }

    @Override
    public Venda save(Venda venda) {
        VendaEntity vendaEntity = vendaEntityMapper.toEntity(venda);
        VendaEntity savedEntity = vendaJpaRepository.save(vendaEntity);
        return vendaEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsById(Integer id) {
        return vendaJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        vendaJpaRepository.deleteById(id);
    }

    @Override
    public List<Venda> findAll() {
        return vendaJpaRepository.findAll()
                .stream()
                .map(vendaEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Venda> findAll(Pageable pageable) {
        return vendaJpaRepository.findAll(pageable)
                .map(vendaEntityMapper::toDomain);
    }

    @Override
    public Venda findById(Integer id) {
        VendaEntity vendaEntity = vendaJpaRepository.findById(id).orElse(null);
        if (vendaEntity == null) {
            return null;
        }
        return vendaEntityMapper.toDomain(vendaEntity);
    }

    @Override
    public List<Venda> findByClienteId(Integer clienteId) {
        return vendaJpaRepository.findByCliente_IdCliente(clienteId)
                .stream()
                .map(vendaEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Venda> findByUsuarioId(Integer usuarioId) {
        return vendaJpaRepository.findByUsuario_Id(usuarioId)
                .stream()
                .map(vendaEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Venda> findByDataBetween(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime dataInicioTime = dataInicio.atStartOfDay();
        LocalDateTime dataFimTime = dataFim.atTime(LocalTime.MAX);
        return vendaJpaRepository.findByDataBetween(dataInicioTime, dataFimTime)
                .stream()
                .map(vendaEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Venda> findByValorTotalBetween(Double valorMinimo, Double valorMaximo) {
        return vendaJpaRepository.findByValorTotalBetween(valorMinimo, valorMaximo)
                .stream()
                .map(vendaEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
