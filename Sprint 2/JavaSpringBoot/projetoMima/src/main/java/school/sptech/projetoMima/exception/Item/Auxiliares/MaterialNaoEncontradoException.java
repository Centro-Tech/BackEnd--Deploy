package school.sptech.projetoMima.exception.Item.Auxiliares;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MaterialNaoEncontradoException extends RuntimeException {
    public MaterialNaoEncontradoException(String message) {
        super(message);
    }
}
