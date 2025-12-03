package school.sptech.projetoMima.infrastructure.persistance.ItemVendaPersistance;

import school.sptech.projetoMima.core.adapter.ItemVenda.ItemVendaGateway;
import school.sptech.projetoMima.core.domain.ItemVenda;

import java.util.List;
import java.util.Optional;

public class ItemVendaJpaAdapter implements ItemVendaGateway {

    private final ItemVendaJpaRepository itemVendaJpaRepository;

    public ItemVendaJpaAdapter(ItemVendaJpaRepository itemVendaJpaRepository,
                              ItemVendaEntityMapper itemVendaEntityMapper) {
        this.itemVendaJpaRepository = itemVendaJpaRepository;
    }

    @Override
    public ItemVenda save(ItemVenda itemVenda) {
        try {
            System.out.println("============ SALVANDO ITEMVENDA ============");
            System.out.println("ItemVenda ID: " + itemVenda.getId());
            System.out.println("Item ID: " + (itemVenda.getItem() != null ? itemVenda.getItem().getId() : "null"));
            System.out.println("Fornecedor ID: " + (itemVenda.getFornecedor() != null ? itemVenda.getFornecedor().getId() : "null"));
            System.out.println("Venda ID: " + (itemVenda.getVenda() != null ? itemVenda.getVenda().getId() : "null"));
            System.out.println("Quantidade: " + itemVenda.getQtdParaVender());
            
            ItemVendaEntity jpaEntity = ItemVendaEntityMapper.toEntity(itemVenda);
            System.out.println("Entity criada, chamando save...");
            
            ItemVendaEntity savedEntity = itemVendaJpaRepository.save(jpaEntity);
            System.out.println("Save retornou ID: " + savedEntity.getId());
            System.out.println("============================================");
            
            return ItemVendaEntityMapper.toDomain(savedEntity);
        } catch (Exception e) {
            System.err.println("============ ERRO AO SALVAR ITEMVENDA ============");
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
            System.err.println("==================================================");
            throw e;
        }
    }

    @Override
    public boolean existsById(Integer id) {
        return itemVendaJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        itemVendaJpaRepository.deleteById(id);
    }

    @Override
    public List<ItemVenda> findAll() {
        return itemVendaJpaRepository.findAll()
                .stream()
                .map(ItemVendaEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<ItemVenda> findById(Integer id) {
        return itemVendaJpaRepository.findById(id)
                .map(ItemVendaEntityMapper::toDomain);
    }

    @Override
    public List<ItemVenda> findByVendaIsNull() {
        return itemVendaJpaRepository.findByVendaIsNull()
                .stream()
                .map(ItemVendaEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<ItemVenda> buscarPorIdEVenda(Integer idItemVenda, Integer idVenda) {
        return itemVendaJpaRepository.buscarPorIdEVenda(idItemVenda, idVenda)
                .map(ItemVendaEntityMapper::toDomain);
    }
}
