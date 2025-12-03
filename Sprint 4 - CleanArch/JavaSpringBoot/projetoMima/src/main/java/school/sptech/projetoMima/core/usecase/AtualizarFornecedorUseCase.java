package school.sptech.projetoMima.core.usecase;

import org.springframework.stereotype.Service;
import school.sptech.projetoMima.core.adapter.Fornecedor.FornecedorGateway;
import school.sptech.projetoMima.core.domain.Fornecedor;

@Service
public class AtualizarFornecedorUseCase {

    private final FornecedorGateway fornecedorGateway;

    public AtualizarFornecedorUseCase(FornecedorGateway fornecedorGateway) {
        this.fornecedorGateway = fornecedorGateway;
    }

    public Fornecedor atualizarFornecedor(Integer id, Fornecedor fornecedorAtualizado) {
        Fornecedor fornecedorExistente = fornecedorGateway.findById(id);
        if (fornecedorExistente == null) {
            throw new RuntimeException("Fornecedor não encontrado");
        }

        if (fornecedorAtualizado.getNome() == null || fornecedorAtualizado.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do fornecedor não pode ser vazio");
        }

        if (fornecedorAtualizado.getEmail() == null || fornecedorAtualizado.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email do fornecedor não pode ser vazio");
        }

        if (!fornecedorAtualizado.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email do fornecedor inválido");
        }

        if (fornecedorAtualizado.getTelefone() == null || fornecedorAtualizado.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do fornecedor não pode ser vazio");
        }

        fornecedorExistente.setNome(fornecedorAtualizado.getNome().trim());
        fornecedorExistente.setTelefone(fornecedorAtualizado.getTelefone().trim());
        fornecedorExistente.setEmail(fornecedorAtualizado.getEmail().trim());

        return fornecedorGateway.update(id, fornecedorExistente);
    }
}
