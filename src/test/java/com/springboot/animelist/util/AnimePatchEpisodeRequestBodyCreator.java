package com.springboot.animelist.util;

import com.springboot.animelist.requests.AnimePatchEpisodeRequestBody;

public class AnimePatchEpisodeRequestBodyCreator {
    public static AnimePatchEpisodeRequestBody animePatchEpisodeRequestBody(){
        AnimePatchEpisodeRequestBody animeRequest = new AnimePatchEpisodeRequestBody();
        animeRequest.setId(1L);
        animeRequest.setEpisode(Short.valueOf("18"));
        return animeRequest;
    }
}
