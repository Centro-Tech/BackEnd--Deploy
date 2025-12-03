package school.sptech.projetoMima.core.adapter.Item.auxiliares;

import school.sptech.projetoMima.core.domain.item.Material;

import java.util.List;

public interface MaterialGateway {
    Material save(Material material);
    boolean existsById(Integer id);
    boolean existsByNome(String nome);
    void deleteById(Integer id);
    List<Material> findAll();
    Material findById(Integer id);
}
