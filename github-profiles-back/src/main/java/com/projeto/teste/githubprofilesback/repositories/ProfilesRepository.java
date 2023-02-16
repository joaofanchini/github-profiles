package com.projeto.teste.githubprofilesback.repositories;

import com.projeto.teste.githubprofilesback.collections.Profiles;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProfilesRepository extends ReactiveMongoRepository<Profiles, Integer> {
    @Query("{ login :  {$eq: ?0}}")
    Mono<Profiles> findByLogin(String login);
}
