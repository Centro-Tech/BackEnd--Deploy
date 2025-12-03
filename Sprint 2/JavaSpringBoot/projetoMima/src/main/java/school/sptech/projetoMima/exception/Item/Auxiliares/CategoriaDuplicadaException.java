package school.sptech.projetoMima.exception.Item.Auxiliares;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CategoriaDuplicadaException extends RuntimeException {
    public CategoriaDuplicadaException(String message) {
        super(message);
    }
}
