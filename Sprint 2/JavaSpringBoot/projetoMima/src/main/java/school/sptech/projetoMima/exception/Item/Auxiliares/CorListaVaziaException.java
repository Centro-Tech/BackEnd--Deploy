package school.sptech.projetoMima.exception.Item.Auxiliares;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class CorListaVaziaException extends RuntimeException {
    public CorListaVaziaException(String message) {
        super(message);
    }
}
