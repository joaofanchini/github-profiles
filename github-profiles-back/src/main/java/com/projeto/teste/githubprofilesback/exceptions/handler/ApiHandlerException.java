package com.projeto.teste.githubprofilesback.exceptions.handler;

import com.projeto.teste.githubprofilesback.exceptions.BaseRuntimeException;
import com.projeto.teste.githubprofilesback.exceptions.IntegrationException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestControllerAdvice
public class ApiHandlerException {

    public ApiHandlerException(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IntegrationException.class)
    public Fault internalServerError(BaseRuntimeException e) {
        return new Fault(getMessage(e.getCode(), e.getParameters()), e);
    }

    private String getMessage(String code, List<String> parameters) {
        return messageSource.getMessage(code, Optional.ofNullable(parameters)
                        .orElse(new ArrayList<>())
                        .toArray(),
                Locale.getDefault());
    }
}
