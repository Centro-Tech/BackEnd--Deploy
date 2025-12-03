package school.sptech.projetoMima.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.projetoMima.entity.Fornecedor;
import school.sptech.projetoMima.exception.Fornecedor.FornecedorExistenteException;
import school.sptech.projetoMima.exception.Fornecedor.FornecedorNaoEncontradoException;
import school.sptech.projetoMima.repository.FornecedorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Optional<Fornecedor> findById(Integer id) {
        return fornecedorRepository.findById(id);
    }

    public List<Fornecedor> listar() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor cadastrar(Fornecedor fornecedor) {
        if(fornecedor == null ||
                fornecedor.getNome() == null ||
                fornecedor.getEmail() == null ||
                fornecedor.getTelefone() == null) {
            throw new NullPointerException("Os campos não podem ser vazios!");
        }
        if (!fornecedorRepository.existsByNome(fornecedor.getNome())) {
            Fornecedor fornecedorCadastrado = fornecedorRepository.save(fornecedor);
            return fornecedorCadastrado;
        }
        throw new FornecedorExistenteException("Fornecedor com este nome já cadastrado!");
    }

    public void deletar(Integer id) {
        Fornecedor fornecedor = findById(id).orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado!"));
        fornecedorRepository.delete(fornecedor);
    }
}
