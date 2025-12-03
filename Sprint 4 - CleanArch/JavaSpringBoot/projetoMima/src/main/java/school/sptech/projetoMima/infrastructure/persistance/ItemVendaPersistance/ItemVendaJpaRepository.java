package school.sptech.projetoMima.infrastructure.persistance.ItemVendaPersistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemVendaJpaRepository extends JpaRepository<ItemVendaEntity, Integer> {

    // Buscar itens no carrinho (sem venda associada)
    List<ItemVendaEntity> findByVendaIsNull();

    @Query("SELECT iv FROM ItemVendaEntity iv WHERE iv.id = :idItemVenda AND iv.venda.id = :idVenda")
    Optional<ItemVendaEntity> buscarPorIdEVenda(@Param("idItemVenda") Integer idItemVenda, @Param("idVenda") Integer idVenda);
}
