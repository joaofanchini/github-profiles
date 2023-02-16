package com.projeto.teste.githubprofilesback.controllers;

import com.projeto.teste.github_profiles_openapi.api.ProfileInfoApi;
import com.projeto.teste.github_profiles_openapi.model.ProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class ProfileInfoController implements ProfileInfoApi {
    @Override
    public Mono<ResponseEntity<ProfileResponse>> getProfileInfo(String userProfile, ServerWebExchange exchange) {
        ProfileResponse profileResponse = new ProfileResponse()
                .id(1)
                .avatarUrl("")
                .login(userProfile);

        return Mono.just(ResponseEntity.ok(profileResponse));
    }
}
