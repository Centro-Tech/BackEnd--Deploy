package school.sptech.projetoMima.core.application.exception.Item;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemQuantidadeInvalida extends RuntimeException {
  public ItemQuantidadeInvalida(String message) {
    super(message);
  }
}
