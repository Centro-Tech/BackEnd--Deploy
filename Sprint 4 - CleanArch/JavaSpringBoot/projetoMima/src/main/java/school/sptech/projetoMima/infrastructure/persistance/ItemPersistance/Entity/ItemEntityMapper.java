package school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.Entity;

import school.sptech.projetoMima.core.domain.item.Item;
import school.sptech.projetoMima.infrastructure.persistance.FornecedorPersistance.FornecedorEntityMapper;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Tamanho.Entity.TamanhoEntityMapper;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Cor.Entity.CorEntityMapper;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Material.Entity.MaterialEntityMapper;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.auxiliares.Categoria.Entity.CategoriaEntityMapper;

public class ItemEntityMapper {

    public static Item toDomain(ItemEntity entity) {
        if (entity == null) return null;

        Item item = new Item();
        item.setId(entity.getId());
        item.setCodigo(entity.getCodigo());
        item.setQtdEstoque(entity.getQtdEstoque());
        item.setNome(entity.getNome());
        item.setPreco(entity.getPreco());
        if (entity.getFornecedor() != null) {
            item.setFornecedor(FornecedorEntityMapper.toDomain(entity.getFornecedor()));
        }

        if (entity.getTamanho() != null) {
            item.setTamanho(TamanhoEntityMapper.toDomain(entity.getTamanho()));
        }
        if (entity.getCor() != null) {
            item.setCor(CorEntityMapper.toDomain(entity.getCor()));
        }
        if (entity.getMaterial() != null) {
            item.setMaterial(MaterialEntityMapper.toDomain(entity.getMaterial()));
        }
        if (entity.getCategoria() != null) {
            item.setCategoria(CategoriaEntityMapper.toDomain(entity.getCategoria()));
        }

        return item;
    }

    public static ItemEntity toEntity(Item domain) {
        if (domain == null) return null;

        ItemEntity entity = new ItemEntity();
        entity.setId(domain.getId());
        entity.setCodigo(domain.getCodigo());
        entity.setQtdEstoque(domain.getQtdEstoque());
        entity.setNome(domain.getNome());
        entity.setPreco(domain.getPreco());
        if (domain.getFornecedor() != null) {
            entity.setFornecedor(FornecedorEntityMapper.toEntity(domain.getFornecedor()));
        }

        if (domain.getTamanho() != null) {
            entity.setTamanho(TamanhoEntityMapper.toEntity(domain.getTamanho()));
        }
        if (domain.getCor() != null) {
            entity.setCor(CorEntityMapper.toEntity(domain.getCor()));
        }
        if (domain.getMaterial() != null) {
            entity.setMaterial(MaterialEntityMapper.toEntity(domain.getMaterial()));
        }
        if (domain.getCategoria() != null) {
            entity.setCategoria(CategoriaEntityMapper.toEntity(domain.getCategoria()));
        }

        return entity;
    }
}
