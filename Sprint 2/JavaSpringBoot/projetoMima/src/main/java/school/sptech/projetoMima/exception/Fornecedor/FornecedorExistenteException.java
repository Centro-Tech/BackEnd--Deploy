package school.sptech.projetoMima.exception.Fornecedor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FornecedorExistenteException extends RuntimeException {
    public FornecedorExistenteException(String message) {
        super(message);
    }
}
