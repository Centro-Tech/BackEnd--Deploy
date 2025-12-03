package school.sptech.projetoMima.core.application.exception.Item.Auxiliares;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class MaterialListaVaziaException extends RuntimeException {
    public MaterialListaVaziaException(String message) {
        super(message);
    }
}
