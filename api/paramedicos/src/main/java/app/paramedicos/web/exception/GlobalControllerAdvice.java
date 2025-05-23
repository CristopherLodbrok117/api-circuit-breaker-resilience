package app.paramedicos.web.exception;

import app.paramedicos.domain.exception.MedicalRecordException;
import app.paramedicos.domain.exception.PatientNotFoundException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import jakarta.validation.ValidationException;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(PatientNotFoundException.class)
    ResponseEntity<Map<String, String>> patientExceptionGandler(PatientNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(MedicalRecordException.class)
    ResponseEntity<Map<String, String>> patientExceptionGandler(MedicalRecordException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<Map<String, String>> patientExceptionGandler(ValidationException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    ResponseEntity<Map<String, String>> patientExceptionGandler(Exception ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }


    @ExceptionHandler(CallNotPermittedException.class)
    ResponseEntity<Map<String, String>> circuitBreakerOpenHandler(CallNotPermittedException ex) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", "Servicio en recuperación. Intente en unos segundos."));
    }
}
