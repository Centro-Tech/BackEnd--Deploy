package school.sptech.projetoMima.Repository;

import org.springframework.data.repository.CrudRepository;
import school.sptech.projetoMima.Model.Funcionario;

import java.util.List;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

}
