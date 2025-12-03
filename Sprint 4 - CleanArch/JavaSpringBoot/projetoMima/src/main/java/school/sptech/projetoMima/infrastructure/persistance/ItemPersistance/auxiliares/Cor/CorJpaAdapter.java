package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Cor;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CorGateway;
import school.sptech.projetoMima.core.domain.item.Cor;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Cor.Entity.CorEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Cor.Entity.CorEntityMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CorJpaAdapter implements CorGateway {

    private final CorJpaRepository corJpaRepository;

    public CorJpaAdapter(CorJpaRepository corJpaRepository) {
        this.corJpaRepository = corJpaRepository;
    }

    @Override
    public Cor save(Cor cor) {
        CorEntity entity = CorEntityMapper.toEntity(cor);
        CorEntity savedEntity = corJpaRepository.save(entity);
        return CorEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsById(Integer id) {
        return corJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByNome(String nome) {
        return corJpaRepository.existsByNome(nome);
    }

    @Override
    public void deleteById(Integer id) {
        corJpaRepository.deleteById(id);
    }

    @Override
    public List<Cor> findAll() {
        List<CorEntity> entities = corJpaRepository.findAll();
        List<Cor> cores = new ArrayList<>();
        for (CorEntity entity : entities) {
            cores.add(CorEntityMapper.toDomain(entity));
        }
        return cores;
    }

    @Override
    public Cor findById(Integer id) {
        CorEntity entity = corJpaRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return CorEntityMapper.toDomain(entity);
    }

    @Override
    public boolean existsByNomeIgnoreCase(String nome) {
        return corJpaRepository.existsByNomeIgnoreCase(nome);
    }
}
