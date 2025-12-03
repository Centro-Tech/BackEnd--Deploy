package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Categoria;

import school.sptech.projetoMima.core.adapter.Item.auxiliares.CategoriaGateway;
import school.sptech.projetoMima.core.domain.item.Categoria;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Categoria.Entity.CategoriaEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Categoria.Entity.CategoriaEntityMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoriaJpaAdapter implements CategoriaGateway {

    private final CategoriaJpaRepository categoriaJpaRepository;

    public CategoriaJpaAdapter(CategoriaJpaRepository categoriaJpaRepository) {
        this.categoriaJpaRepository = categoriaJpaRepository;
    }

    @Override
    public Categoria save(Categoria categoria) {
        CategoriaEntity entity = CategoriaEntityMapper.toEntity(categoria);
        CategoriaEntity savedEntity = categoriaJpaRepository.save(entity);
        return CategoriaEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsById(Integer id) {
        return categoriaJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByNome(String nome) {
        return categoriaJpaRepository.existsByNome(nome);
    }

    @Override
    public void deleteById(Integer id) {
        categoriaJpaRepository.deleteById(id);
    }

    @Override
    public List<Categoria> findAll() {
        List<CategoriaEntity> entities = categoriaJpaRepository.findAll();
        List<Categoria> categorias = new ArrayList<>();
        for (CategoriaEntity entity : entities) {
            categorias.add(CategoriaEntityMapper.toDomain(entity));
        }
        return categorias;
    }

    @Override
    public Categoria findById(Integer id) {
        CategoriaEntity entity = categoriaJpaRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return CategoriaEntityMapper.toDomain(entity);
    }

    @Override
    public boolean existsByNomeIgnoreCase(String nome) {
return  categoriaJpaRepository.existsByNomeIgnoreCase(nome);  }
}
