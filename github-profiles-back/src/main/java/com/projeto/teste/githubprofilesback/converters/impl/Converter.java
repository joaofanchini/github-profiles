package com.projeto.teste.githubprofilesback.converters.impl;

public interface Converter<IN, OUT> {
    OUT converter(IN in);

    Class<IN> clazzFrom();

    Class<OUT> clazzTo();
}
