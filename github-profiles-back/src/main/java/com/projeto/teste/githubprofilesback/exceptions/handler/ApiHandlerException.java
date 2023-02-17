package com.projeto.teste.githubprofilesback.exceptions.handler;

import com.projeto.teste.github_profiles_openapi.model.Fault;
import com.projeto.teste.githubprofilesback.exceptions.BaseRuntimeException;
import com.projeto.teste.githubprofilesback.exceptions.IntegrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestControllerAdvice
public class ApiHandlerException {

    private final Logger log = LoggerFactory.getLogger(ApiHandlerException.class);
    public ApiHandlerException(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IntegrationException.class)
    public Fault integrationException(BaseRuntimeException e) {
        log.error("Handler error", e);
        final String message = getMessage(e.getCode(), e.getParameters());

        return buildFault(message, e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Fault exception(Exception e) {
        log.error("Handler error", e);

        return buildFault(e.getMessage(), e);
    }

    private Fault buildFault(String message, Exception e){
        return new Fault().message(message)
                .exception(e.getClass().getSimpleName())
                .cause(e.getCause().getClass().getSimpleName())
                .timestamp(Instant.now().toEpochMilli());
    }

    private String getMessage(String code, List<String> parameters) {
        return messageSource.getMessage(code, Optional.ofNullable(parameters)
                        .orElse(new ArrayList<>())
                        .toArray(),
                Locale.getDefault());
    }
}
