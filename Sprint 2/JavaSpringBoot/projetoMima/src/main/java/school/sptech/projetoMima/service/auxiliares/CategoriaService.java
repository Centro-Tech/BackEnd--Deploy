package school.sptech.projetoMima.service.auxiliares;

import org.springframework.stereotype.Service;
import school.sptech.projetoMima.entity.item.Categoria;
import school.sptech.projetoMima.exception.Item.Auxiliares.CategoriaDuplicadaException;
import school.sptech.projetoMima.exception.Item.Auxiliares.CategoriaInvalidoException;
import school.sptech.projetoMima.exception.Item.Auxiliares.CategoriaListaVaziaException;
import school.sptech.projetoMima.exception.Item.Auxiliares.CategoriaNaoEncontradoException;
import school.sptech.projetoMima.repository.auxiliares.CategoriaRepository;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> listar() {
        List<Categoria> categorias = repository.findAll();
        if (categorias.isEmpty()) {
            throw new CategoriaListaVaziaException("Nenhuma categoria encontrada.");
        }
        return categorias;
    }

    public Categoria buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradoException("Categoria com ID " + id + " não encontrada"));
    }

    public Categoria salvar(Categoria categoria) {
        if (categoria == null) {
            throw new CategoriaInvalidoException("Categoria não pode ser nula.");
        }

        if (repository.existsByNomeIgnoreCase(categoria.getNome())) {
            throw new CategoriaDuplicadaException("Categoria com nome '" + categoria.getNome() + "' já está cadastrada.");
        }

        return repository.save(categoria);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new CategoriaNaoEncontradoException("Categoria com ID " + id + " não encontrada para exclusão.");
        }

        repository.deleteById(id);
    }
}
