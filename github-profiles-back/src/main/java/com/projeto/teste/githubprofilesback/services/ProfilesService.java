package com.projeto.teste.githubprofilesback.services;

import com.projeto.teste.githubprofilesback.collections.Profiles;
import com.projeto.teste.githubprofilesback.exceptions.DocumentNotFoundException;
import com.projeto.teste.githubprofilesback.repositories.ProfilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProfilesService {

    @Autowired
    public ProfilesService(ProfilesRepository profilesRepository) {
        this.profilesRepository = profilesRepository;
    }

    private final ProfilesRepository profilesRepository;

    public Mono<Profiles> findByLogin(String login) {
        return this.profilesRepository.findByLogin(login)
                .switchIfEmpty(Mono.error(new DocumentNotFoundException("error.document.notFound")));
    }
}
