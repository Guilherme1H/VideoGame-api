package org.acme;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> { // Mudamos para Exception para pegar tudo

    public static class ErrorResponse {
        public String message;
        public Map<String, String> details;

        public ErrorResponse(String message, Map<String, String> details) {
            this.message = message;
            this.details = details;
        }
    }

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) exception;
            Map<String, String> errors = ex.getConstraintViolations().stream()
                    .collect(Collectors.toMap(
                            cv -> cv.getPropertyPath().toString(),
                            ConstraintViolation::getMessage
                    ));

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Erro de validação.", errors))
                    .build();
        }

        if (exception instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Recurso não encontrado.", Map.of("error", exception.getMessage())))
                    .build();
        }

        exception.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Ocorreu um erro interno no servidor.", Map.of("error_type", exception.getClass().getSimpleName())))
                .build();
    }
}