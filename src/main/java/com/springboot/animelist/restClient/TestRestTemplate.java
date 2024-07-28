package com.springboot.animelist.restClient;

import com.springboot.animelist.domain.Anime;
import com.springboot.animelist.requests.AnimePatchNameRequestBody;
import com.springboot.animelist.requests.AnimePostRequestBody;
import com.springboot.animelist.requests.AnimePutRequestBody;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.hibernate.dialect.JsonHelper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/anime/rest-template")
@Log4j2
public class TestRestTemplate {

    @GetMapping
    public void executeRestTemplate(){
        listAll();
        findById();
        findByName();
        save();
        replace();
        remove();
    }

    public void listAll(){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Anime>> entity = restTemplate
                .exchange("http://localhost:8080/api/v1/animes/list-all", HttpMethod.GET
                ,null, new ParameterizedTypeReference<>() {});

        log.info(entity.getBody());
    }

    public void findById(){
        String url = "http://localhost:8080/api/v1/animes/{id}";
        RestTemplate restTemplate = new RestTemplate();

        Anime anime = restTemplate.getForObject(url , Anime.class, 9L);

        log.info(anime);
    }


    public void findByName(){
        String url = "http://localhost:8080/api/v1/animes/find-by-name";
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("name", "Knife");
        String uriBuilder = builder.build().encode().toUriString();

        ResponseEntity<List<Anime>> entity = restTemplate.exchange( uriBuilder, HttpMethod.GET
                , null, new ParameterizedTypeReference<>() {});

        log.info(entity.getBody());
    }


    public void save(){
        String url = "http://localhost:8080/api/v1/animes";
        Anime anime = Anime.builder().name("Knife").episode((short) 13).build();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Anime> entity = restTemplate.postForEntity(url, anime, Anime.class);

        assert entity.getStatusCode() == HttpStatus.CREATED;

        log.info(entity.getBody());
    }

    public void replace(){
        AnimePutRequestBody animePut = new AnimePutRequestBody();
        animePut.setId(5L);
        animePut.setName("ToDay");
        animePut.setEpisode((short) 40L);

        new RestTemplate().put("http://localhost:8080/api/v1/animes", animePut);
    }


    public void remove(){
        Anime anime = new RestTemplate()
                .getForObject("http://localhost:8080/api/v1/animes/{id}", Anime.class, 6);

        new RestTemplate().delete("http://localhost:8080/api/v1/animes/{id}", anime.getId());
    }

    public static HttpHeaders createJsonHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


}
