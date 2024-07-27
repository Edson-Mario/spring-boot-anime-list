package com.springboot.animelist.util;

import com.springboot.animelist.requests.AnimePatchNameRequestBody;

public class AnimePatchNameRequestBodyCreator {
    public static AnimePatchNameRequestBody animePatchNameRequestBody(){
        AnimePatchNameRequestBody animeRequest = new AnimePatchNameRequestBody();
        animeRequest.setId(1L);
        animeRequest.setName("One Peace");
        return animeRequest;
    }
}
