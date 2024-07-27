package com.springboot.animelist.service;

import com.springboot.animelist.domain.Anime;
import com.springboot.animelist.exception.BadRequestException;
import com.springboot.animelist.mapper.AnimeMapper;
import com.springboot.animelist.repository.AnimeRepository;
import com.springboot.animelist.requests.AnimePostRequestBody;
import com.springboot.animelist.requests.AnimePatchEpisodeRequestBody;
import com.springboot.animelist.requests.AnimePatchNameRequestBody;
import com.springboot.animelist.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public Page<Anime> listAll(Pageable pageable){
        return animeRepository.findAll(pageable);
    }

    public List<Anime> listAllNonPageable(){
        return animeRepository.findAll();
    }

    public Anime findByIdOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found"));
    }

    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody){
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }


    @Transactional
    public void replace(AnimePutRequestBody animePutRequestBody){
        Anime animeSaved = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime animeToSave = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        animeToSave.setId(animeSaved.getId());
        animeRepository.save(animeToSave);
    }

    @Transactional
    public Anime replaceName(AnimePatchNameRequestBody animePutNameRequestBody){
        Anime animeSaved = findByIdOrThrowBadRequestException(animePutNameRequestBody.getId());
        animeSaved.setName(animePutNameRequestBody.getName());
        return animeRepository.save(animeSaved);
    }

    @Transactional
    public Anime replaceEpisode(AnimePatchEpisodeRequestBody animePutEpisodeRequestBody){
        Anime animeSaved = findByIdOrThrowBadRequestException(animePutEpisodeRequestBody.getId());
        animeSaved.setEpisode(animePutEpisodeRequestBody.getEpisode());
        return animeRepository.save(animeSaved);
    }

    public void remove(long id){
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
