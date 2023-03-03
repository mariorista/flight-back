package mar.cod.flightback.utils.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> exceptionsMap(MethodArgumentNotValidException ex) {
        // new HashMap<>();
        Map<String, String> map = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        // return ResponseEntity.ok(map);
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<String> exceptionNotFoundEntity(Exception e) {
        return ResponseEntity.status(444).body(e.getMessage());
    }

    @ExceptionHandler(UnmetConditionsException.class)
    public ResponseEntity<String> exceptionUnmetConditions(Exception e) {
        return ResponseEntity.status(454).body(e.getMessage());
    }

    @ExceptionHandler(NoResultsAvalilable.class)
    public ResponseEntity<String> exceptionNoResultsAvailable(Exception e) {
        return ResponseEntity.status(464).body(e.getMessage());
    }

    @ExceptionHandler(OperationNotPosible.class)
    public ResponseEntity<String> exceptionOperationNotPosible(Exception e) {
        return ResponseEntity.status(474).body(e.getMessage());
    }

    
}
