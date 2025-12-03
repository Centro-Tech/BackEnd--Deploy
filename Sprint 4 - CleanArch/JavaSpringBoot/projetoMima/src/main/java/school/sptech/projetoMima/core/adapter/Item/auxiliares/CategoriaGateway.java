package school.sptech.projetoMima.core.adapter.Item.auxiliares;

import school.sptech.projetoMima.core.domain.item.Categoria;

import java.util.List;

public interface CategoriaGateway {
    Categoria save(Categoria categoria);
    boolean existsById(Integer id);
    boolean existsByNome(String nome);
    void deleteById(Integer id);
    List<Categoria> findAll();
    Categoria findById(Integer id);
    boolean existsByNomeIgnoreCase(String nome);
}
