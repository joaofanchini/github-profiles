package com.projeto.teste.githubprofilesback.builders;

import com.projeto.teste.githubprofilesback.collections.Profiles;

public class FollowersDefaultBuilder {
    public Profiles.Followers getDefault() {
        return Profiles.Followers.builder()
                .id(1)
                .login("loginFollower")
                .build();
    }
}
