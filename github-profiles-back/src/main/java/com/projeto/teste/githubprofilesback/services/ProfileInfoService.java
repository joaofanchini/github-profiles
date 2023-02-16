package com.projeto.teste.githubprofilesback.services;

import com.projeto.teste.github_openapi.model.MinimalRepository;
import com.projeto.teste.github_openapi.model.PublicUser;
import com.projeto.teste.github_openapi.model.SimpleUser;
import com.projeto.teste.github_profiles_openapi.model.ProfileResponse;
import com.projeto.teste.github_profiles_openapi.model.ProfileResponseFollowersInner;
import com.projeto.teste.github_profiles_openapi.model.ProfileResponseRepositoriesInner;
import com.projeto.teste.githubprofilesback.collections.Profiles;
import com.projeto.teste.githubprofilesback.integrations.GitHubIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.projeto.teste.githubprofilesback.collections.Profiles.*;

@Service
public class ProfileInfoService {

    @Autowired
    public ProfileInfoService(ProfilesService profilesService,
                              GitHubIntegration gitHubIntegration) {
        this.profilesService = profilesService;
        this.gitHubIntegration = gitHubIntegration;
    }

    private final ProfilesService profilesService;
    private final GitHubIntegration gitHubIntegration;

    public Mono<ProfileResponse> getProfileInfo(String login) {
        return Mono.just(login)
                .flatMap(lg -> this.profilesService.findByLogin(lg)
                        .switchIfEmpty(Mono.defer(() -> {
                                            Mono<PublicUser> monoGeneralInformation = gitHubIntegration.getGeneralInformation(lg)
                                                    .subscribeOn(Schedulers.boundedElastic());
                                            Mono<SimpleUser[]> monoFollowers = gitHubIntegration.getFollowers(lg)
                                                    .subscribeOn(Schedulers.boundedElastic());
                                            Mono<MinimalRepository[]> monoRepositoriesFlux = gitHubIntegration.getRepositories(lg)
                                                    .subscribeOn(Schedulers.boundedElastic());

                                            return Mono.zip(monoFollowers, monoRepositoriesFlux, monoGeneralInformation);
                                        })
                                        .map(tuple -> {
                                            List<SimpleUser> listFollowersResponse = Arrays.asList(tuple.getT1());
                                            List<MinimalRepository> listRepositoriesResponse = Arrays.asList(tuple.getT2());
                                            PublicUser generalInfoResponse = tuple.getT3();

                                            return Tuples.of(generalInfoResponse, listFollowersResponse, listRepositoriesResponse);
                                        })
                                        .flatMap(tuple -> {
                                            PublicUser generalInfoResponse = tuple.getT1();
                                            List<SimpleUser> listFollowersResponse = tuple.getT2();
                                            List<MinimalRepository> listRepositoriesResponse = tuple.getT3();
                                            return Mono.defer(() -> {
                                                Profiles profile = builder()
                                                        .id(generalInfoResponse.getId())
                                                        .avatarUrl(generalInfoResponse.getAvatarUrl().toString())
                                                        .login(generalInfoResponse.getLogin())
                                                        .followers(listFollowersResponse.stream()
                                                                .map(f -> Followers.builder().id(f.getId()).login(f.getLogin()).build())
                                                                .collect(Collectors.toList()))
                                                        .repositories(listRepositoriesResponse.stream()
                                                                .map(r -> Repositories.builder().id(r.getId()).name(r.getFullName()).build())
                                                                .collect(Collectors.toList()))
                                                        .build();
                                                return this.profilesService.insertOrUpdate(profile);
                                            });
                                        })
                        ))
                .map(profile -> new ProfileResponse()
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
                                .collect(Collectors.toList())));
    }
}
