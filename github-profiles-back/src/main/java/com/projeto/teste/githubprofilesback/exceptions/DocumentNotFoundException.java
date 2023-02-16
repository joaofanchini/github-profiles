package com.projeto.teste.githubprofilesback.exceptions;

import org.apache.logging.log4j.util.Strings;

public class DocumentNotFoundException extends BaseRuntimeException{
    public DocumentNotFoundException(String code, Throwable cause, String... parameters) {
        super(code, cause, parameters);
    }

    public DocumentNotFoundException(String code) {
        super(code, null, Strings.EMPTY_ARRAY);
    }
}
