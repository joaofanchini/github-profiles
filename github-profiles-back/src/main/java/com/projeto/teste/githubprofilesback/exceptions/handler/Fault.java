package com.projeto.teste.githubprofilesback.exceptions.handler;

public class Fault {
    private final String message;
    private final Throwable e;

    public Fault(String message, Throwable e) {
        this.message = message;
        this.e = e;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getE() {
        return e;
    }
}
