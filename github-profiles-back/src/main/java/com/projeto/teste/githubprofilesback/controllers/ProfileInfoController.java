package com.projeto.teste.githubprofilesback.controllers;

import com.projeto.teste.github_profiles_openapi.api.ProfileInfoApi;
import com.projeto.teste.github_profiles_openapi.model.ProfileResponse;
import com.projeto.teste.githubprofilesback.services.ProfileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class ProfileInfoController implements ProfileInfoApi {

    @Autowired
    public ProfileInfoController(ProfileInfoService profileInfoService) {
        this.profileInfoService = profileInfoService;
    }

    private final ProfileInfoService profileInfoService;

    @Override
    public Mono<ResponseEntity<ProfileResponse>> getProfileInfo(String userProfile, ServerWebExchange exchange) {
        return profileInfoService.getProfileInfo(userProfile)
                .map(ResponseEntity::ok);
    }
}
