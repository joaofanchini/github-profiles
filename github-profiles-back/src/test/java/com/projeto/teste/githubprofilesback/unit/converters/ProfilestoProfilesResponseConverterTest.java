package com.projeto.teste.githubprofilesback.unit.converters;

import com.projeto.teste.github_profiles_openapi.model.ProfileResponse;
import com.projeto.teste.github_profiles_openapi.model.ProfileResponseFollowersInner;
import com.projeto.teste.github_profiles_openapi.model.ProfileResponseRepositoriesInner;
import com.projeto.teste.githubprofilesback.builders.ProfilesDefaultBuilder;
import com.projeto.teste.githubprofilesback.collections.Profiles;
import com.projeto.teste.githubprofilesback.converters.impl.ProfilesToProfileResponseConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class ProfilestoProfilesResponseConverterTest {

    private final ProfilesToProfileResponseConverter converter = new ProfilesToProfileResponseConverter();


    @Test
    public void shouldConverterProfilesToProfilesResponse() {
        Profiles profiles = ProfilesDefaultBuilder.getDefault();
        ProfileResponse profileResponse = converter.converter(profiles);

        Assertions.assertEquals(profiles.getId(), profileResponse.getId());
        Assertions.assertEquals(profiles.getLogin(), profileResponse.getLogin());
        Assertions.assertEquals(profiles.getAvatarUrl(), profileResponse.getAvatarUrl());

        List<Profiles.Repositories> repositoriesProfiles = profiles.getRepositories();
        List<ProfileResponseRepositoriesInner> repositoriesProfileResponse = profileResponse.getRepositories();

        repositoriesProfiles.forEach(repo -> {
            Optional<ProfileResponseRepositoriesInner> optionalProfileResponse = repositoriesProfileResponse.stream()
                    .filter(repoProfileResponse -> repoProfileResponse.getId().equals(repo.getId()))
                    .findFirst();

            Assertions.assertTrue(optionalProfileResponse.isPresent());

            ProfileResponseRepositoriesInner profileResponseRepositoriesInner = optionalProfileResponse.get();

            Assertions.assertEquals(repo.getId(), profileResponseRepositoriesInner.getId());
            Assertions.assertEquals(repo.getName(), profileResponseRepositoriesInner.getName());
        });

        List<Profiles.Followers> followers = profiles.getFollowers();
        List<ProfileResponseFollowersInner> followersProfileResponse = profileResponse.getFollowers();

        followers.forEach(follower -> {
            Optional<ProfileResponseFollowersInner> optionalFollower = followersProfileResponse.stream()
                    .filter(followerResp -> followerResp.getId().equals(follower.getId()))
                    .findFirst();

            Assertions.assertTrue(optionalFollower.isPresent());

            ProfileResponseFollowersInner followerResponse = optionalFollower.get();

            Assertions.assertEquals(follower.getId(), followerResponse.getId());
            Assertions.assertEquals(follower.getLogin(), followerResponse.getLogin());
        });
    }

    @Test
    public void testClazzFromClazzTo() {
        Assertions.assertTrue(converter.clazzFrom().isAssignableFrom(Profiles.class));
        Assertions.assertTrue(converter.clazzTo().isAssignableFrom(ProfileResponse.class));
    }

}
