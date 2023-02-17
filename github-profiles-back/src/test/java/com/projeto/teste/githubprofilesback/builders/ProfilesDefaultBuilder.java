package com.projeto.teste.githubprofilesback.builders;

import com.projeto.teste.githubprofilesback.collections.Profiles;

import java.util.Arrays;

import static java.util.Arrays.asList;

public class ProfilesDefaultBuilder {

    private static final FollowersDefaultBuilder followersDefaultBuilder = new FollowersDefaultBuilder();
    private static final RepositoriesDefaultBuilder repositoriesDefaultBuilder = new RepositoriesDefaultBuilder();

    public static Profiles getDefault(){
        return Profiles.builder()
                .id(1)
                .login("login")
                .avatarUrl("avatarUrl")
                .repositories(asList(repositoriesDefaultBuilder.getDefault()))
                .followers(asList(followersDefaultBuilder.getDefault()))
                .build();
    }
}
