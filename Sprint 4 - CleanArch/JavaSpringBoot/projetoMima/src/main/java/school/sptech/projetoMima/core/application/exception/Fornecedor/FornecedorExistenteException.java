package school.sptech.projetoMima.core.application.exception.Fornecedor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FornecedorExistenteException extends RuntimeException {
    public FornecedorExistenteException(String message) {
        super(message);
    }
}
