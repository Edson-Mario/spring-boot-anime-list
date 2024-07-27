package com.springboot.animelist.util;

import com.springboot.animelist.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSave(){
        return Anime.builder()
                .name("Boruto")
                .episode(Short.valueOf("14"))
                .build();
    }

    public static Anime createValidAnimeToUpdate(){
        return Anime.builder()
                .id(1L)
                .name("Naruto")
                .episode(Short.valueOf("16"))
                .build();
    }

    public static  Anime createValidAnime(){
        return Anime.builder()
                .id(1L)
                .name("Boruto")
                .episode(Short.valueOf("14"))
                .build();

    }

    public static Anime createAnimeWithEmptyName(){
        return Anime.builder()
                .episode(Short.valueOf("14"))
                .build();
    }

    public static Anime createAnimeWithNullEpisode(){
        return Anime.builder()
                .name("Boruto")
                .build();
    }
}
