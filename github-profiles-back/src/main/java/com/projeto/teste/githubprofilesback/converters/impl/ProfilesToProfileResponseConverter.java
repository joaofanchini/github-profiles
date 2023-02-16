package com.projeto.teste.githubprofilesback.converters.impl;

import com.projeto.teste.github_profiles_openapi.model.ProfileResponse;
import com.projeto.teste.github_profiles_openapi.model.ProfileResponseFollowersInner;
import com.projeto.teste.github_profiles_openapi.model.ProfileResponseRepositoriesInner;
import com.projeto.teste.githubprofilesback.collections.Profiles;
import com.projeto.teste.githubprofilesback.converters.impl.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProfilesToProfileResponseConverter implements Converter<Profiles, ProfileResponse> {

    @Override
    public ProfileResponse converter(Profiles profile) {
        return new ProfileResponse()
                .id(profile.getId())
                .login(profile.getLogin())
                .avatarUrl(profile.getAvatarUrl())
                .followers(profile.getFollowers().stream()
                        .map(follower -> new ProfileResponseFollowersInner()
                                .id(follower.getId())
                                .login(follower.getLogin()))
                        .collect(Collectors.toList()))
                .repositories(profile.getRepositories()
                        .stream()
                        .map(repo -> new ProfileResponseRepositoriesInner()
                                .id(repo.getId())
                                .name(repo.getName()))
                        .collect(Collectors.toList()));
    }

    @Override
    public Class<Profiles> clazzFrom() {
        return Profiles.class;
    }

    @Override
    public Class<ProfileResponse> clazzTo() {
        return ProfileResponse.class;
    }
}
