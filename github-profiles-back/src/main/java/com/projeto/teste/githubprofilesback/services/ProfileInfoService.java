package com.projeto.teste.githubprofilesback.services;

import com.projeto.teste.github_openapi.model.MinimalRepository;
import com.projeto.teste.github_openapi.model.PublicUser;
import com.projeto.teste.github_openapi.model.SimpleUser;
import com.projeto.teste.github_profiles_openapi.model.ProfileResponse;
import com.projeto.teste.githubprofilesback.collections.Profiles;
import com.projeto.teste.githubprofilesback.converters.ConverterFacade;
import com.projeto.teste.githubprofilesback.integrations.GitHubIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple3;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.projeto.teste.githubprofilesback.collections.Profiles.*;

@Service
public class ProfileInfoService {

    @Autowired
    public ProfileInfoService(ProfilesService profilesService,
                              GitHubIntegration gitHubIntegration,
                              ConverterFacade converterFacade) {
        this.profilesService = profilesService;
        this.gitHubIntegration = gitHubIntegration;
        this.converterFacade = converterFacade;
    }

    private final ProfilesService profilesService;
    private final GitHubIntegration gitHubIntegration;
    private final ConverterFacade converterFacade;

    public Mono<ProfileResponse> getProfileInfo(String login) {
        return Mono.deferContextual(ctx -> this.profilesService.findByLogin(login).contextWrite(ctx))
                .switchIfEmpty(Mono.defer(() -> callToGithubIntegration(login))
                        .flatMap(this::insertProfile))
                .map(profile -> converterFacade.converter(profile, ProfileResponse.class));
    }

    private Mono<? extends Profiles> insertProfile(Tuple3<PublicUser, List<SimpleUser>, List<MinimalRepository>> tuple) {
        PublicUser generalInfoResponse = tuple.getT1();
        List<SimpleUser> listFollowersResponse = tuple.getT2();
        List<MinimalRepository> listRepositoriesResponse = tuple.getT3();

        List<Followers> followers = listFollowersResponse.stream()
                .map(follower -> Followers.builder()
                        .id(follower.getId())
                        .login(follower.getLogin())
                        .build())
                .collect(Collectors.toList());

        List<Repositories> repositories = listRepositoriesResponse.stream()
                .map(r -> Repositories.builder().id(r.getId()).name(r.getFullName()).build())
                .collect(Collectors.toList());

        String avatarUrl = Optional.ofNullable(generalInfoResponse.getAvatarUrl())
                .map(URI::toString)
                .orElse(null);

        Profiles profile = builder()
                .id(generalInfoResponse.getId())
                .login(generalInfoResponse.getLogin())
                .avatarUrl(avatarUrl)
                .followers(followers)
                .repositories(repositories)
                .build();

        return this.profilesService.insertOrUpdate(profile);
    }

    private Mono<Tuple3<PublicUser, List<SimpleUser>, List<MinimalRepository>>> callToGithubIntegration(String login) {
        Mono<PublicUser> monoGeneralInformation = gitHubIntegration.getGeneralInformation(login)
                .subscribeOn(Schedulers.boundedElastic());

        Mono<List<SimpleUser>> monoFollowers = gitHubIntegration.getFollowers(login)
                .subscribeOn(Schedulers.boundedElastic());

        Mono<List<MinimalRepository>> monoRepositories = gitHubIntegration.getRepositories(login)
                .subscribeOn(Schedulers.boundedElastic());

        return Mono.zip(monoGeneralInformation, monoFollowers, monoRepositories);
    }
}
