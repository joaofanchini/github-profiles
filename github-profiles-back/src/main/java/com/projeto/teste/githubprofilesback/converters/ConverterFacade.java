package com.projeto.teste.githubprofilesback.converters;

import com.projeto.teste.githubprofilesback.converters.impl.Converter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConverterFacade {

    public ConverterFacade(List<Converter> converters) {
        this.mapConverters = converters.stream()
                .collect(Collectors.toMap(k -> new KeyConverter(k.clazzFrom(), k.clazzTo()), v -> v));
    }

    private final Map<KeyConverter, Converter> mapConverters;

    @SuppressWarnings({"unchecked"})
    public <T, R> R converter(T object, Class<? super R> clazzTo) {
        KeyConverter key = new KeyConverter(object.getClass(), clazzTo);

        Object convertedObject = Optional.ofNullable(mapConverters.getOrDefault(key, null))
                .map(converter -> converter.converter(object))
                .orElse(null);

        return (R) convertedObject;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class KeyConverter {
        private final Class<?> clazzFrom;
        private final Class<?> clazzto;
    }
}
