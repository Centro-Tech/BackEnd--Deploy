package school.sptech.projetoMima.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.projetoMima.entity.ItemVenda;

import java.util.List;
import java.util.Optional;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Integer> {
    List<ItemVenda> findByClienteIdAndVendaIsNull(Integer clienteId);

    @Query("SELECT iv FROM ItemVenda iv JOIN FETCH iv.venda WHERE iv.id = :id")
    Optional<ItemVenda> buscarComVenda(@Param("id") Integer id);

    @Query("SELECT iv FROM ItemVenda iv WHERE iv.id = :idItemVenda AND iv.venda.id = :idVenda")
    Optional<ItemVenda> buscarPorIdEVenda(@Param("idItemVenda") Integer idItemVenda,
                                          @Param("idVenda") Integer idVenda);

    @Query("SELECT iv FROM ItemVenda iv JOIN FETCH iv.venda WHERE iv.id = :idItemVenda AND iv.venda.id = :idVenda")
    Optional<ItemVenda> buscarPorIdEVendaComJoin(@Param("idItemVenda") Integer idItemVenda,
                                                 @Param("idVenda") Integer idVenda);

    @Query("SELECT iv FROM ItemVenda iv WHERE iv.cliente.id = :clienteId AND iv.venda IS NULL")
    List<ItemVenda> buscarCarrinhoParaFinalizar(@Param("clienteId") Integer clienteId);



}

