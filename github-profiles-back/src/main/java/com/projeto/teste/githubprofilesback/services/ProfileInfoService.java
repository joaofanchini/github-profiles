package com.projeto.teste.githubprofilesback.services;

import com.projeto.teste.github_profiles_openapi.model.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProfileInfoService {

    @Autowired
    public ProfileInfoService(ProfilesService profilesService) {
        this.profilesService = profilesService;
    }

    private final ProfilesService profilesService;

    public Mono<ProfileResponse> getProfileInfo(String login) {
        return profilesService.findByLogin(login)
                .map(profile -> new ProfileResponse()
                        .id(profile.getId())
                        .login(profile.getLogin())
                        .avatarUrl(profile.getAvatarUrl()));
    }
}
