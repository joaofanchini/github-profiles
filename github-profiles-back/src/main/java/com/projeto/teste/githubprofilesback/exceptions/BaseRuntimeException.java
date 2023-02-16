package com.projeto.teste.githubprofilesback.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class BaseRuntimeException extends RuntimeException {

    public BaseRuntimeException(String code, Throwable cause, String... parameters) {
        super(code, cause);
        this.code = code;
        if(Objects.nonNull(parameters)) {
            this.parameters = Arrays.asList(parameters);
        }
    }

    private final String code;
    private List<String> parameters = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public List<String> getParameters() {
        return parameters;
    }
}
