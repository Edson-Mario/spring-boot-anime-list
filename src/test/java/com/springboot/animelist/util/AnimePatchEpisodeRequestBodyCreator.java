package com.springboot.animelist.util;

import com.springboot.animelist.requests.AnimePatchEpisodeRequestBody;

public class AnimePatchEpisodeRequestBodyCreator {
    public static AnimePatchEpisodeRequestBody animePatchEpisodeRequestBody(){
        AnimePatchEpisodeRequestBody animeRequest = new AnimePatchEpisodeRequestBody();
        animeRequest.setEpisode(Short.valueOf("14"));
        return animeRequest;
    }
}
