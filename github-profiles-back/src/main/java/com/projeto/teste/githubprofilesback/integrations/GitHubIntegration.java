package com.projeto.teste.githubprofilesback.integrations;

import com.projeto.teste.github_openapi.model.MinimalRepository;
import com.projeto.teste.github_openapi.model.PublicUser;
import com.projeto.teste.github_openapi.model.SimpleUser;
import com.projeto.teste.githubprofilesback.exceptions.IntegrationException;
import com.projeto.teste.githubprofilesback.integrations.filters.Filters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class GitHubIntegration {

    private static final Logger log = LoggerFactory.getLogger(GitHubIntegration.class);

    @Autowired
    public GitHubIntegration(@Value("${github.api.baseUrl}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .filter(Filters.filtroAddHeader(HttpHeaders.ACCEPT, "application/vnd.github+json"))
                .filter(Filters.logRequest())
                .filter(Filters.logResponse())
                .build();
    }

    private final WebClient webClient;

    public Mono<PublicUser> getGeneralInformation(String login) {
        return this.webClient.get()
                .uri(builder -> builder.path("/users/{username}")
                        .build(login))
                .retrieve()
                .bodyToMono(PublicUser.class)
                .onErrorMap(e -> new IntegrationException("error.github.integration", e, "getGeneralInformation"));
    }

    public Mono<List<MinimalRepository>> getRepositories(String login) {
        return this.webClient.get()
                .uri(builder -> builder.path("/users/{username}/repos")
                        .build(login))
                .retrieve()
                .bodyToMono(MinimalRepository[].class)
                .map(Arrays::asList)
                .onErrorMap(e -> new IntegrationException("error.github.integration", e, "getRepositories"));
    }

    public Mono<List<SimpleUser>> getFollowers(String login) {
        return this.webClient.get()
                .uri(builder -> builder.path("/users/{username}/followers")
                        .build(login))
                .retrieve()
                .bodyToMono(SimpleUser[].class)
                .map(Arrays::asList)
                .onErrorMap(e -> new IntegrationException("error.github.integration", e, "getFollowers"));

    }
}
