package com.springboot.animelist.service;

import com.springboot.animelist.controller.AnimeController;
import com.springboot.animelist.domain.Anime;
import com.springboot.animelist.exception.BadRequestException;
import com.springboot.animelist.repository.AnimeRepository;
import com.springboot.animelist.requests.AnimePatchEpisodeRequestBody;
import com.springboot.animelist.requests.AnimePatchNameRequestBody;
import com.springboot.animelist.requests.AnimePostRequestBody;
import com.springboot.animelist.requests.AnimePutRequestBody;
import com.springboot.animelist.util.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService animeService;
    @Mock
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(animePage.toList());

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(animePage.toList().get(0)));

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(animePage.toList());

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(animePage.toList().get(0));

        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }

    @Test
    @DisplayName("listAll return list of anime inside of page object when successful")
    void listAll_ReturnsListOfAnimeInsideOfPageObject_WhenSuccessful(){
        var expectedId = AnimeCreator.createValidAnime().getId();

        Page<Anime> animePage =  animeService.listAll(PageRequest.of(0, 1));

        Assertions.assertThat(animePage).isNotEmpty();

        Assertions.assertThat(animePage.toList().get(0).getId())
                .isNotNull()
                .isEqualTo(expectedId);

        Assertions.assertThat(animePage.toList().get(0).getName())
                .isNotEmpty();

        Assertions.assertThat(animePage.toList().get(0).getEpisode())
                .isNotNull();
    }

    @Test
    @DisplayName("listAllNonPageable return list of anime when successful")
    void listAllNonPageable_ReturnsListOfAnime_WhenSuccessful(){
        var expectedId = AnimeCreator.createValidAnime().getId();

        List<Anime> animes = animeService.listAllNonPageable();

        Assertions.assertThat(animes)
                .isNotEmpty();

        Assertions.assertThat(animes.get(0).getId())
                .isNotNull()
                .isEqualTo(expectedId);

        Assertions.assertThat(animes.get(0).getName())
                .isNotEmpty();

        Assertions.assertThat(animes.get(0).getEpisode())
                .isNotNull();
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException return anime when successful")
    void findByIdOrThrowBadRequestException_ReturnsAnime_WhenSuccessful(){
        var expectedId = AnimeCreator.createValidAnime().getId();

        Anime animes = animeService.findByIdOrThrowBadRequestException(1L);

        Assertions.assertThat(animes).isNotNull();

        Assertions.assertThat(animes.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when Anime is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenSuccessful(){
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                        .isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1L))
                .withMessageContaining("Anime not found");
    }


    @Test
    @DisplayName("findByName return list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        var expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeService.findByName("anime");

        Assertions.assertThat(animes).isNotEmpty();

        Assertions.assertThat(animes.get(0).getName())
                .isNotNull()
                .isEqualTo(expectedName);

        Assertions.assertThat(animes.get(0).getId())
                .isNotNull();

        Assertions.assertThat(animes.get(0).getEpisode())
                .isNotNull();
    }

    @Test
    @DisplayName("findByName return empty anime list anime is not found")
    void findByName_ReturnsEmptyAnimeList_WhenAnimeIsNotFound(){
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeService.findByName("anime");

        Assertions.assertThat(animes).isEmpty();
    }


    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccess(){
        var expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeService.save(AnimePostRequestBodyCreator.animePostRequestBody());

        Assertions.assertThat(anime)
                .isNotNull();

        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);

        Assertions.assertThat(anime.getName())
                .isNotEmpty();

        Assertions.assertThat(anime.getEpisode())
                .isNotNull();
    }

    @Test
    @DisplayName("replaceName update name of anime when successful")
    void replaceName_ReturnAnime_WhenSuccessful(){
        var oldName = AnimeCreator.createAnimeToBeSave().getName();

        Anime anime = animeService.replaceName(AnimePatchNameRequestBodyCreator.animePatchNameRequestBody());

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getName())
                .isNotNull()
                .isNotEqualTo(oldName);
    }

    @Test
    @DisplayName("replaceEpisode update episode of anime when successful")
    void replaceEpisode_ReturnAnime_WhenSuccessful(){
        var oldEpisode = AnimeCreator.createAnimeToBeSave().getEpisode();

        Anime anime =  animeService.replaceEpisode(AnimePatchEpisodeRequestBodyCreator.animePatchEpisodeRequestBody());

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getEpisode())
                .isNotNull()
                .isNotEqualTo(oldEpisode);
    }


    @Test
    @DisplayName("replace update anime when successful")
    void replace_UpdateAnime_WhenSuccessful(){
        Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.animePutRequestBody()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("remove delete anime when successful")
    void remove_DeleteAnime_WhenSuccessful(){
         Assertions.assertThatCode(()-> animeService.remove(1L))
                 .doesNotThrowAnyException();
    }

}