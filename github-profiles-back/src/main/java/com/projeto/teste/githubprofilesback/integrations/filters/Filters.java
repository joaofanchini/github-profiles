package com.projeto.teste.githubprofilesback.integrations.filters;

import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class Filters {

    public static ExchangeFilterFunction filtroAddHeader(String headerName, String headerValue) {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> Mono.deferContextual(ctx ->
                Mono.just(ClientRequest.from(clientRequest)
                                .header(headerName, headerValue)
                                .build())
                        .contextWrite(ctx)));
    }

}
