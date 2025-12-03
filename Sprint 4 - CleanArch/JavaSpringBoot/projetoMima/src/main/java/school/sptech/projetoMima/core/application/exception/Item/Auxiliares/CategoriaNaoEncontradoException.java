package school.sptech.projetoMima.core.application.exception.Item.Auxiliares;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoriaNaoEncontradoException extends RuntimeException {
  public CategoriaNaoEncontradoException(String message) {
    super(message);
  }
}
