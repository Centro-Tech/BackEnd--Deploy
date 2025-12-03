package school.sptech.projetoMima.exception.Item.Auxiliares;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class CategoriaListaVaziaException extends RuntimeException {
    public CategoriaListaVaziaException(String message) {
        super(message);
    }
}
