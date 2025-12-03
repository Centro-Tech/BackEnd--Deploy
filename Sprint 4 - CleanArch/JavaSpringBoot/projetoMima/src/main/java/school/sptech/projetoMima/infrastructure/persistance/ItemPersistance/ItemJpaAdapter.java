package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.projetoMima.core.adapter.Item.ItemGateway;
import school.sptech.projetoMima.core.domain.item.Item;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.Entity.ItemEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.Entity.ItemEntityMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemJpaAdapter implements ItemGateway {

    private final ItemJpaRepository repository;

    public ItemJpaAdapter(ItemJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Item save(Item item) {
        ItemEntity entity = ItemEntityMapper.toEntity(item);
        ItemEntity savedEntity = repository.save(entity);
        return ItemEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByCodigo(String codigo) {
        return repository.existsByCodigo(codigo);
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
    public void deleteByCodigo(String codigo) {
        repository.deleteByCodigo(codigo);
    }

    @Override
    public List<Item> findAll() {
        List<ItemEntity> entities = repository.findAll();
        List<Item> itens = new ArrayList<>();
        for (ItemEntity entity : entities) {
            itens.add(ItemEntityMapper.toDomain(entity));
        }
        return itens;
    }

    @Override
    public Page<Item> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ItemEntityMapper::toDomain);
    }

    @Override
    public Item findById(Integer id) {
        ItemEntity entity = repository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return ItemEntityMapper.toDomain(entity);
    }

    @Override
    public Item findByCodigo(String codigo) {
        ItemEntity entity = repository.findByCodigo(codigo).orElse(null);
        if (entity == null) {
            return null;
        }
        return ItemEntityMapper.toDomain(entity);
    }

    @Override
    public List<Item> findByCategoriaNomeContainsIgnoreCase(String nomeCategoria) {
        List<ItemEntity> entities = repository.findByCategoriaNomeContainsIgnoreCase(nomeCategoria);
        List<Item> itens = new ArrayList<>();
        for (ItemEntity entity : entities) {
            itens.add(ItemEntityMapper.toDomain(entity));
        }
        return itens;
    }

    @Override
    public List<Item> findByFornecedorNomeContainsIgnoreCase(String nome) {
        List<ItemEntity> entities = repository.findByFornecedorNomeContainsIgnoreCase(nome);
        List<Item> itens = new ArrayList<>();
        for (ItemEntity entity : entities) {
            itens.add(ItemEntityMapper.toDomain(entity));
        }
        return itens;
    }

    @Override
    public List<Item> findByNomeContainsIgnoreCase(String nome) {
        List<ItemEntity> entities = repository.findByNomeContainsIgnoreCase(nome);
        List<Item> itens = new ArrayList<>();
        for (ItemEntity entity : entities) {
            itens.add(ItemEntityMapper.toDomain(entity));
        }
        return itens;
    }

    @Override
    public List<Item> findByCodigoContainsIgnoreCase(String codigo) {
        List<ItemEntity> entities = repository.findByCodigoContainsIgnoreCase(codigo);
        List<Item> itens = new ArrayList<>();
        for (ItemEntity entity : entities) {
            itens.add(ItemEntityMapper.toDomain(entity));
        }
        return itens;
    }
    
    @Override
    public boolean existsByMaterialId(Integer materialId) {
        return repository.existsByMaterialId(materialId);
    }
}
