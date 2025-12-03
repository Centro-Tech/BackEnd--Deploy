package school.sptech.projetoMima.core.application.exception.Item.Auxiliares;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class TamanhoListaVaziaException extends RuntimeException {
    public TamanhoListaVaziaException(String message) {
        super(message);
    }
}
