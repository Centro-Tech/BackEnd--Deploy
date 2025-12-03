package school.sptech.projetoMima.infrastructure.persistance.FornecedorPersistance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.projetoMima.core.adapter.Fornecedor.FornecedorGateway;
import school.sptech.projetoMima.core.domain.Fornecedor;
import java.util.ArrayList;
import java.util.List;
import static school.sptech.projetoMima.infrastructure.persistance.FornecedorPersistance.FornecedorEntityMapper.toDomain;

@Service
public class FornecedorJpaAdapter implements FornecedorGateway {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorJpaAdapter(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public boolean existsByNome(String nome) {
        return fornecedorRepository.existsByNome(nome);
    }


    @Override
    public boolean isNullOrEmpty(String str) {
        return false;
    }

    @Override
    public boolean existsById(Integer id) {
        return fornecedorRepository.existsById(id);
    }

    @Override
    public Fornecedor findById(Integer id) {
        FornecedorEntity entity = fornecedorRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new RuntimeException("Fornecedor não encontrado");
        }
        return toDomain(entity);
    }

    @Override
    public List<Fornecedor> findAll() {
        List<FornecedorEntity> entity = fornecedorRepository.findAll();

        List<Fornecedor> fornecedores = new ArrayList<>();

        for (FornecedorEntity fornecedorEntity : entity) {
            Fornecedor fornecedor = FornecedorEntityMapper.toDomain(fornecedorEntity);
            fornecedores.add(fornecedor);
        }

        return fornecedores;
    }

    @Override
    public Page<Fornecedor> findAll(Pageable pageable) {
        return fornecedorRepository.findAll(pageable)
                .map(FornecedorEntityMapper::toDomain);
    }

    @Override
    public void deleteById(Integer id) {
        fornecedorRepository.deleteById(id);
    }

    @Override
    public Fornecedor save(Fornecedor fornecedor) {
        FornecedorEntity fornecedorEntity = fornecedorRepository.save(FornecedorEntityMapper.toEntity(fornecedor));
        return toDomain(fornecedorEntity);
    }

    @Override
    public Fornecedor update(Integer id, Fornecedor fornecedorAtualizado) {
        FornecedorEntity fornecedorEntity = fornecedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));

        fornecedorEntity.setNome(fornecedorAtualizado.getNome());
        fornecedorEntity.setTelefone(fornecedorAtualizado.getTelefone());
        fornecedorEntity.setEmail(fornecedorAtualizado.getEmail());

        FornecedorEntity updatedEntity = fornecedorRepository.save(fornecedorEntity);
        return FornecedorEntityMapper.toDomain(updatedEntity);
    }
}
