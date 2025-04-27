package app.paramedicos.web.exception;

import app.paramedicos.domain.exception.MedicalRecordException;
import app.paramedicos.domain.exception.PatientNotFoundException;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
