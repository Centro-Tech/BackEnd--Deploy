package school.sptech.projetoMima.core.adapter.Item.auxiliares;

import school.sptech.projetoMima.core.domain.item.Cor;

import java.util.List;

public interface CorGateway {
    Cor save(Cor cor);
    boolean existsById(Integer id);
    boolean existsByNome(String nome);
    void deleteById(Integer id);
    List<Cor> findAll();
    Cor findById(Integer id);
    boolean existsByNomeIgnoreCase(String nome);
}
