package com.project.gestionutilisateur.Exception;


import java.time.LocalDateTime;
import java.util.List;

public class ValidationErrorResponse extends ErrorResponse {
    private List<String> errors;

    public ValidationErrorResponse() {}

    public ValidationErrorResponse(int status, String message, LocalDateTime timestamp,
                                   String path, List<String> errors) {
        super(status, message, timestamp, path);
        this.errors = errors;
    }

    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}
