package school.sptech.projetoMima.exception.Item.Auxiliares;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TamanhoDuplicadoException extends RuntimeException {
    public TamanhoDuplicadoException(String message) {
        super(message);
    }
}
