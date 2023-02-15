package com.projeto.teste.githubprofilesback.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseRuntimeException extends RuntimeException {

    public BaseRuntimeException(String code, Throwable cause, String... parameters) {
        super(code, cause);
        this.code = code;
        this.parameters = Arrays.asList(parameters);
    }

    private final String code;
    private final List<String> parameters;

    public String getCode() {
        return code;
    }

    public List<String> getParameters() {
        return parameters;
    }
}
