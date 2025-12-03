package school.sptech.projetoMima.exception.Usuario;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class UsuarioListaVaziaException extends RuntimeException {
    public UsuarioListaVaziaException(String message) {
        super(message);
    }
}
