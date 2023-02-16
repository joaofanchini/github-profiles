package com.projeto.teste.githubprofilesback.collections;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profiles-cl")
public class Profiles {
    @MongoId
    private Integer id;

    @Indexed
    @Field
    private String login;

    @Field
    private String avatarUrl;

    @Field
    private List<Repositories> repositories;

    @Field
    private List<Followers> followers;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Followers {
        @Indexed(unique = true)
        private Integer id;

        @Field
        private String login;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Repositories {
        @Indexed(unique = true)
        private Integer id;

        @Field
        private String name;
    }
}
