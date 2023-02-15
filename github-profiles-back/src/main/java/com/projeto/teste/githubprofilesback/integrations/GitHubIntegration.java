package com.projeto.teste.githubprofilesback.integrations;

import com.projeto.teste.githubprofilesback.exceptions.IntegrationException;
import com.projeto.teste.githubprofilesback.integrations.filters.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GitHubIntegration {

    @Autowired
    public GitHubIntegration(@Value("${github.api.baseUrl}") String baseUrl) {
        this.webClient = WebClient.builder()
                .filter(Filters.filtroAddHeader(HttpHeaders.ACCEPT, "application/vnd.github+json"))
                .baseUrl(baseUrl)
                .build();
    }

    private final WebClient webClient;

    public Mono<String> getGeneralInformation(String username) {
        return this.webClient.get()
                .uri(builder -> builder.path("/users/{username}")
                        .build(username))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorMap(e -> new IntegrationException("error.github.integration", e, "getGeneralInformation"));
    }

    public Flux<String> getRepositories(String username) {
        return this.webClient.get()
                .uri(builder -> builder.path("/users/{username}/repos")
                        .build(username))
                .retrieve()
                .bodyToFlux(String.class)
                .onErrorMap(e -> new IntegrationException("error.github.integration", e, "getRepositories"));
    }

    public Flux<String> getFollowers(String username) {
        return this.webClient.get()
                .uri(builder -> builder.path("/users/{username}/followers")
                        .build(username))
                .retrieve()
                .bodyToFlux(String.class)
                .onErrorMap(e -> new IntegrationException("error.github.integration", e, "getFollowers"));
    }
}
