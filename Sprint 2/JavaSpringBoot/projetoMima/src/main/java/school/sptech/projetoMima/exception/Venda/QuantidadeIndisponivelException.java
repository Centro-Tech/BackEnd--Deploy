package school.sptech.projetoMima.exception.Venda;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuantidadeIndisponivelException extends RuntimeException {
    public QuantidadeIndisponivelException(String message) {
        super(message);
    }
}
