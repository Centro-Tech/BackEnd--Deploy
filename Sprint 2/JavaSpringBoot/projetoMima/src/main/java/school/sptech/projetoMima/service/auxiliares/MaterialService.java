package school.sptech.projetoMima.service.auxiliares;

import org.springframework.stereotype.Service;
import school.sptech.projetoMima.entity.item.Material;
import school.sptech.projetoMima.exception.Item.Auxiliares.MaterialDuplicadoException;
import school.sptech.projetoMima.exception.Item.Auxiliares.MaterialInvalidoException;
import school.sptech.projetoMima.exception.Item.Auxiliares.MaterialListaVaziaException;
import school.sptech.projetoMima.exception.Item.Auxiliares.MaterialNaoEncontradoException;
import school.sptech.projetoMima.repository.auxiliares.MaterialRepository;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository repository;

    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
    }

    public List<Material> listar() {
        List<Material> materiais = repository.findAll();
        if (materiais.isEmpty()) {
            throw new MaterialListaVaziaException("Nenhum material encontrado.");
        }
        return materiais;
    }

    public Material buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new MaterialNaoEncontradoException("Material com ID " + id + " não encontrado"));
    }

    public Material salvar(Material material) {
        if (material == null) {
            throw new MaterialInvalidoException("Material inválido: objeto nulo.");
        }

        if (repository.existsByNomeIgnoreCase(material.getNome())) {
            throw new MaterialDuplicadoException("Material com nome '" + material.getNome() + "' já está cadastrado.");
        }

        return repository.save(material);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new MaterialNaoEncontradoException("Material com ID " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }
}
