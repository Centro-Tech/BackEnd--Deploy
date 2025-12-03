package school.sptech.projetoMima.exception.Fornecedor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FornecedorNaoEncontradoException extends RuntimeException {
    public FornecedorNaoEncontradoException(String message) {
        super(message);
    }
}
