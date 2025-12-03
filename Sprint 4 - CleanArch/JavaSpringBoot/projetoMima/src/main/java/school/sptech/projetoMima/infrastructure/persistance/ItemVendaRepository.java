package school.sptech.projetoMima.infrastructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.projetoMima.infrastructure.persistance.ItemVendaPersistance.ItemVendaEntity;

import java.util.List;
import java.util.Optional;

public interface ItemVendaRepository extends JpaRepository<ItemVendaEntity, Integer> {
    // Buscar itens no carrinho (sem venda associada)
    List<ItemVendaEntity> findByVendaIsNull();

    @Query("SELECT iv FROM ItemVendaEntity iv JOIN FETCH iv.venda WHERE iv.id = :id")
    Optional<ItemVendaEntity> buscarComVenda(@Param("id") Integer id);

    @Query("SELECT iv FROM ItemVendaEntity iv WHERE iv.id = :idItemVenda AND iv.venda.id = :idVenda")
    Optional<ItemVendaEntity> buscarPorIdEVenda(@Param("idItemVenda") Integer idItemVenda,
                                                @Param("idVenda") Integer idVenda);

    @Query("SELECT iv FROM ItemVendaEntity iv JOIN FETCH iv.venda WHERE iv.id = :idItemVenda AND iv.venda.id = :idVenda")
    Optional<ItemVendaEntity> buscarPorIdEVendaComJoin(@Param("idItemVenda") Integer idItemVenda,
                                                       @Param("idVenda") Integer idVenda);

    // Buscar itens do carrinho (sem venda) - não filtramos mais por cliente pois ItemVenda não tem cliente direto
    @Query("SELECT iv FROM ItemVendaEntity iv WHERE iv.venda IS NULL")
    List<ItemVendaEntity> buscarCarrinhoParaFinalizar();
}
