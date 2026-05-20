package cl.duoc.beatbase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.dao.DataAccessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {
        System.out.println("[GlobalExceptionHandler] Error de validacion en los datos recibidos.");
        StringBuilder detalle = new StringBuilder();
        for (FieldError campo : ex.getBindingResult().getFieldErrors()) {
            detalle.append(campo.getField()).append(": ").append(campo.getDefaultMessage()).append(" | ");
        }
        ApiError error = new ApiError(400, "Error de validacion", detalle.toString());
        return ResponseEntity.badRequest().body(error);
    }

 
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> handleDatabaseError(DataAccessException ex) {
        System.out.println("[GlobalExceptionHandler] ERROR EN BASE DE DATOS: " + ex.getMessage());
        
        ApiError error = new ApiError(500, 
            "Error de comunicación o restricción en la base de datos local", 
            "Revisa si es que Laragon esta encendido.");
            
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiError> handleWebClientError(WebClientResponseException ex) {
        System.out.println("[GlobalExceptionHandler] Error de API externa. Estado HTTP: " + ex.getStatusCode());
        ApiError error = new ApiError(ex.getStatusCode().value(), "Error en servicio externo", ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericError(Exception ex) {
        System.out.println("[GlobalExceptionHandler] Error interno no controlado: " + ex.getMessage());
        ApiError error = new ApiError(500, "Error interno del servidor", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}