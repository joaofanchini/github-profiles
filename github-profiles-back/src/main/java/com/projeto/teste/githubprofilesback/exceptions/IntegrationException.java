package com.projeto.teste.githubprofilesback.exceptions;

public class IntegrationException extends BaseRuntimeException{
    public IntegrationException(String code, Throwable cause, String... parameters) {
        super(code, cause, parameters);
    }
}
