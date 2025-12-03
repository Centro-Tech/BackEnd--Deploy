package school.sptech.projetoMima.infrastructure.persistance.ItemVendaPersistance;

import school.sptech.projetoMima.core.domain.ItemVenda;
import school.sptech.projetoMima.core.domain.Cliente;
import school.sptech.projetoMima.core.domain.Usuario;
import school.sptech.projetoMima.core.domain.Venda;
import school.sptech.projetoMima.core.domain.Fornecedor;
import school.sptech.projetoMima.infrastructure.persistance.ClientePersistance.Enitity.ClienteEntity;
import school.sptech.projetoMima.infrastructure.persistance.UsuarioPersistance.UsuarioEntity;
import school.sptech.projetoMima.infrastructure.persistance.VendaPersistance.VendaEntity;
import school.sptech.projetoMima.infrastructure.persistance.FornecedorPersistance.FornecedorEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.Entity.ItemEntity;
import school.sptech.projetoMima.infrastructure.persistance.ItemPersistance.Entity.ItemEntityMapper;
import school.sptech.projetoMima.core.domain.item.Item;

public class ItemVendaEntityMapper {

    public static ItemVendaEntity toEntity(ItemVenda domainEntity) {
        if (domainEntity == null) return null;

        ItemVendaEntity entity = new ItemVendaEntity();
        entity.setId(domainEntity.getId());
        entity.setQtdParaVender(domainEntity.getQtdParaVender());

        if (domainEntity.getItem() != null) {
            entity.setItem(ItemEntityMapper.toEntity(domainEntity.getItem()));
        }

        if (domainEntity.getFornecedor() != null) {
            FornecedorEntity fornecedorEntity = new FornecedorEntity();
            fornecedorEntity.setId(domainEntity.getFornecedor().getId());
            fornecedorEntity.setNome(domainEntity.getFornecedor().getNome());
            fornecedorEntity.setTelefone(domainEntity.getFornecedor().getTelefone());
            fornecedorEntity.setEmail(domainEntity.getFornecedor().getEmail());
            entity.setFornecedor(fornecedorEntity);
        }

        if (domainEntity.getVenda() != null) {
            VendaEntity vendaEntity = new VendaEntity();
            vendaEntity.setId(domainEntity.getVenda().getId());
            entity.setVenda(vendaEntity);
        }

        return entity;
    }

    public static ItemVenda toDomain(ItemVendaEntity entity) {
        if (entity == null) return null;

        ItemVenda domainEntity = new ItemVenda();
        domainEntity.setId(entity.getId());
        domainEntity.setQtdParaVender(entity.getQtdParaVender());

        if (entity.getItem() != null) {
            domainEntity.setItem(ItemEntityMapper.toDomain(entity.getItem()));
        }

        // Cliente e Funcionário são obtidos da Venda, não diretamente do ItemVenda
        if (entity.getVenda() != null) {
            Venda venda = new Venda();
            venda.setId(entity.getVenda().getId());
            domainEntity.setVenda(venda);
        }

        if (entity.getFornecedor() != null) {
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(entity.getFornecedor().getId());
            fornecedor.setNome(entity.getFornecedor().getNome());
            fornecedor.setTelefone(entity.getFornecedor().getTelefone());
            fornecedor.setEmail(entity.getFornecedor().getEmail());
            domainEntity.setFornecedor(fornecedor);
        }

        return domainEntity;
    }
}
