package school.sptech.projetoMima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class
ProjetoMimaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoMimaApplication.class, args);
	}

}
