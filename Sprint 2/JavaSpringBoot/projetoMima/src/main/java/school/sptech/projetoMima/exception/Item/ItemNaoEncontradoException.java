package school.sptech.projetoMima.exception.Item;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemNaoEncontradoException extends RuntimeException {
  public ItemNaoEncontradoException(String message) {
    super(message);
  }
}
