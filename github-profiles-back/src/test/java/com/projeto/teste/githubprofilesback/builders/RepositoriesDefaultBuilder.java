package com.projeto.teste.githubprofilesback.builders;

import com.projeto.teste.githubprofilesback.collections.Profiles;

public class RepositoriesDefaultBuilder {
    public Profiles.Repositories getDefault() {
        return Profiles.Repositories.builder()
                .id(1)
                .name("nameRepository")
                .build();
    }
}
