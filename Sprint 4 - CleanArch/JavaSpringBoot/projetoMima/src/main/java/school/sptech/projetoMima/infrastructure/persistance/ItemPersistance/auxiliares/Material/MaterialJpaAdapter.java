package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Material;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.MaterialGateway;
import school.sptech.projetoMima.core.domain.item.Material;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Material.Entity.MaterialEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Material.Entity.MaterialEntityMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MaterialJpaAdapter implements MaterialGateway {

    private final MaterialJpaRepository materialJpaRepository;

    public MaterialJpaAdapter(MaterialJpaRepository materialJpaRepository) {
        this.materialJpaRepository = materialJpaRepository;
    }

    @Override
    public Material save(Material material) {
        MaterialEntity entity = MaterialEntityMapper.toEntity(material);
        MaterialEntity savedEntity = materialJpaRepository.save(entity);
        return MaterialEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsById(Integer id) {
        return materialJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByNome(String nome) {
        return materialJpaRepository.existsByNome(nome);
    }

    @Override
    public void deleteById(Integer id) {
        materialJpaRepository.deleteById(id);
    }

    @Override
    public List<Material> findAll() {
        List<MaterialEntity> entities = materialJpaRepository.findAll();
        List<Material> materiais = new ArrayList<>();
        for (MaterialEntity entity : entities) {
            materiais.add(MaterialEntityMapper.toDomain(entity));
        }
        return materiais;
    }

    @Override
    public Material findById(Integer id) {
        MaterialEntity entity = materialJpaRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return MaterialEntityMapper.toDomain(entity);
    }
}
