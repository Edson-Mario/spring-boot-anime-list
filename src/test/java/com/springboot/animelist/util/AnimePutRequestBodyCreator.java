package com.springboot.animelist.util;

import com.springboot.animelist.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {
    public static AnimePutRequestBody animePutRequestBody(){
        AnimePutRequestBody animeRequest = new AnimePutRequestBody();
        animeRequest.setId(1L);
        animeRequest.setName("Naruto");
        animeRequest.setEpisode(Short.valueOf("14"));
        return animeRequest;
    }
}
