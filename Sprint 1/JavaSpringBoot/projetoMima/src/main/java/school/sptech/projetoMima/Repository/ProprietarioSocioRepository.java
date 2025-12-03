package school.sptech.projetoMima.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.projetoMima.Model.ProprietarioSocio;

import java.util.List;

@Repository
public interface ProprietarioSocioRepository extends JpaRepository<ProprietarioSocio, Integer> {

}
