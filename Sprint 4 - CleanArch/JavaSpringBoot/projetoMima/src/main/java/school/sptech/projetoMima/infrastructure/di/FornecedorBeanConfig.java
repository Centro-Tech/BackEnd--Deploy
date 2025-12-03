package school.sptech.projetoMima.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sptech.projetoMima.core.application.usecase.Fornecedor.BuscarFornecedorPorIdUseCase;
import school.sptech.projetoMima.core.application.usecase.Fornecedor.CadastrarFornecedorUseCase;
import school.sptech.projetoMima.core.application.usecase.Fornecedor.DeletarFornecedorUseCase;
import school.sptech.projetoMima.core.application.usecase.Fornecedor.ListarFornecedoresUseCase;
import school.sptech.projetoMima.infrastructure.persistance.FornecedorPersistance.FornecedorJpaAdapter;

@Configuration
public class FornecedorBeanConfig {

    @Bean public CadastrarFornecedorUseCase criarFornecedorUseCase(FornecedorJpaAdapter adapter) {
        return new CadastrarFornecedorUseCase(adapter);
    }

    @Bean public DeletarFornecedorUseCase deletarFornecedorUseCase(FornecedorJpaAdapter adapter) {
        return new DeletarFornecedorUseCase(adapter);
    }

    @Bean public BuscarFornecedorPorIdUseCase buscarFornecedorPorIdUseCase(FornecedorJpaAdapter adapter) {
        return new BuscarFornecedorPorIdUseCase(adapter);
    }

    @Bean public ListarFornecedoresUseCase listarFornecedoresUseCase(FornecedorJpaAdapter adapter) {
        return new ListarFornecedoresUseCase(adapter);
    }
}

