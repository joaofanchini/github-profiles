package com.projeto.teste.githubprofilesback.it;

import com.projeto.teste.githubprofilesback.repositories.ProfilesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITProfileInfo {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProfilesRepository profilesRepository;

    @Test
    public void shouldConsultToGitHubApiAndSaveInMongoDbAndReturn200() {
        WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri(builder -> builder.path("/profile-info/{userProfile}")
                        .build("joaofanchini"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        responseSpec.expectStatus()
                .isOk();

        responseSpec.expectHeader()
                .contentType(MediaType.APPLICATION_JSON);

        responseSpec.expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.login").isNotEmpty()
                .jsonPath("$.avatarUrl").isNotEmpty()
                .jsonPath("$.repositories").isArray()
                .jsonPath("$.followers").isArray();

        StepVerifier.create(profilesRepository.findByLogin("joaofanchini"))
                .expectNextMatches(Objects::nonNull)
                .expectComplete()
                .verify();
    }

    @Test
    public void shoudHandlerIntegrationError(){
        WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri(builder -> builder.path("/profile-info/{userProfile}")
                        .build("joaofanchni"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        responseSpec.expectStatus()
                .is5xxServerError();

        responseSpec.expectHeader()
                .contentType(MediaType.APPLICATION_JSON);

        responseSpec.expectBody()
                .jsonPath("$.message").isNotEmpty()
                .jsonPath("$.exception").isNotEmpty()
                .jsonPath("$.cause").isNotEmpty()
                .jsonPath("$.timestamp").isNotEmpty();
    }
}
