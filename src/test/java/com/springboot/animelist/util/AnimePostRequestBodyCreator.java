package com.springboot.animelist.util;

import com.springboot.animelist.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {
    public static AnimePostRequestBody animePostRequestBody(){
        AnimePostRequestBody animeRequest = new AnimePostRequestBody();
        animeRequest.setName("Naruto");
        animeRequest.setEpisode(Short.valueOf("14"));
        return animeRequest;
    }
}
