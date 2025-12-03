package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Tamanho;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.TamanhoGateway;
import school.sptech.projetoMima.core.domain.item.Tamanho;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Tamanho.Entity.TamanhoEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Tamanho.Entity.TamanhoEntityMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TamanhoJpaAdapter implements TamanhoGateway {

    private final TamanhoJpaRepository tamanhoJpaRepository;

    public TamanhoJpaAdapter(TamanhoJpaRepository tamanhoJpaRepository) {
        this.tamanhoJpaRepository = tamanhoJpaRepository;
    }

    @Override
    public Tamanho save(Tamanho tamanho) {
        TamanhoEntity entity = TamanhoEntityMapper.toEntity(tamanho);
        TamanhoEntity savedEntity = tamanhoJpaRepository.save(entity);
        return TamanhoEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsById(Integer id) {
        return tamanhoJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByNome(String nome) {
        return tamanhoJpaRepository.existsByNome(nome);
    }

    @Override
    public void deleteById(Integer id) {
        tamanhoJpaRepository.deleteById(id);
    }

    @Override
    public List<Tamanho> findAll() {
        List<TamanhoEntity> entities = tamanhoJpaRepository.findAll();
        List<Tamanho> tamanhos = new ArrayList<>();
        for (TamanhoEntity entity : entities) {
            tamanhos.add(TamanhoEntityMapper.toDomain(entity));
        }
        return tamanhos;
    }

    @Override
    public Tamanho findById(Integer id) {
        TamanhoEntity entity = tamanhoJpaRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return TamanhoEntityMapper.toDomain(entity);
    }

    @Override
    public boolean existsByNomeIgnoreCase(String nome) {
        return tamanhoJpaRepository.existsByNomeIgnoreCase(nome);
    }
}
