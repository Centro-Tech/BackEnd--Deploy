package school.sptech.projetoMima.infrastructure.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import school.sptech.projetoMima.core.application.exception.Venda.CarrinhoVazioException;
import school.sptech.projetoMima.core.application.exception.Venda.EstoqueInsuficienteException;
import school.sptech.projetoMima.core.application.exception.Venda.QuantidadeIndisponivelException;

@RestControllerAdvice
public class VendaExceptionHandler {

    @ExceptionHandler(CarrinhoVazioException.class)
    public ResponseEntity<String> handleCarrinhoVazio(CarrinhoVazioException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<String> handleEstoqueInsuficiente(EstoqueInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(QuantidadeIndisponivelException.class)
    public ResponseEntity<String> handleQuantidadeIndisponivel(QuantidadeIndisponivelException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
