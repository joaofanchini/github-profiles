package com.projeto.teste.githubprofilesback.integrations.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Filters {

    private static final Logger log = LoggerFactory.getLogger(Filters.class);

    public static ExchangeFilterFunction filtroAddHeader(String headerName, String headerValue) {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> Mono.deferContextual(ctx ->
                Mono.just(ClientRequest.from(clientRequest)
                                .header(headerName, headerValue)
                                .build())
                        .contextWrite(ctx)));
    }

    public static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            StringBuilder sb = new StringBuilder();
            List<String> headers = new ArrayList<>();
            sb.append("Request ")
                    .append(clientRequest.method())
                    .append(" ")
                    .append(clientRequest.url())
                    .append(" | ")
                    .append("Headers - ");

            clientRequest.headers().keySet()
                    .forEach(header -> headers.add(header + ": " + clientRequest.headers().get(header)));

            sb.append(String.join(" | ", headers));

            log.info(sb.toString());
            return Mono.just(clientRequest);
        });
    }

    public static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            StringBuilder sb = new StringBuilder();
            List<String> headers = new ArrayList<>();

            sb.append("Response status: ")
                    .append(clientResponse.statusCode())
                    .append(" | ")
                    .append("Headers: ");

            clientResponse.headers().asHttpHeaders().keySet()
                    .forEach(k -> headers.add(k + ": " + clientResponse.headers().asHttpHeaders().get(k)));

            sb.append(String.join(" | ", headers));

            log.info(sb.toString());

            return Mono.just(clientResponse);
        });
    }

}
