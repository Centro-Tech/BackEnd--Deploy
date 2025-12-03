package school.sptech.projetoMima.core.application.exception.Item.Auxiliares;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TamanhoInvalidoException extends RuntimeException {
    public TamanhoInvalidoException(String message) {
        super(message);
    }
}
