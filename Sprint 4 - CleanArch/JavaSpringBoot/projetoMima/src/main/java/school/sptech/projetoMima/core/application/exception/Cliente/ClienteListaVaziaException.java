package school.sptech.projetoMima.core.application.exception.Cliente;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClienteListaVaziaException extends RuntimeException {
    public ClienteListaVaziaException(String message) {
        super(message);
    }
}
