package com.springboot.animelist.controller;

import com.springboot.animelist.domain.Anime;
import com.springboot.animelist.requests.AnimePostRequestBody;
import com.springboot.animelist.requests.AnimePatchEpisodeRequestBody;
import com.springboot.animelist.requests.AnimePatchNameRequestBody;
import com.springboot.animelist.requests.AnimePutRequestBody;
import com.springboot.animelist.service.AnimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/animes")
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<Page<Anime>> list(Pageable pageable){
        return new ResponseEntity<>(animeService.listAll(pageable), HttpStatus.OK);
    }

    @GetMapping("list-all")
    public ResponseEntity<List<Anime>> listAll(){
        return new ResponseEntity<>(animeService.listAllNonPageable(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
        return new ResponseEntity<>(animeService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
    }

    @GetMapping("/find-by-name")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name){
        return new ResponseEntity<>(animeService.findByName(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody){
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @PatchMapping("/update-name")
    public ResponseEntity<Anime> replaceName(@RequestBody @Valid AnimePatchNameRequestBody animePutNameRequestBody){
        return new ResponseEntity<>(animeService.replaceName(animePutNameRequestBody), HttpStatus.OK);
    }

    @PatchMapping("/update-episode")
    public ResponseEntity<Anime> replaceEpisode(@RequestBody @Valid AnimePatchEpisodeRequestBody animePutEpisodeRequestBody){
        return new ResponseEntity<>(animeService.replaceEpisode(animePutEpisodeRequestBody), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid AnimePutRequestBody animePutRequestBody){
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> remove(@PathVariable long id){
        animeService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
