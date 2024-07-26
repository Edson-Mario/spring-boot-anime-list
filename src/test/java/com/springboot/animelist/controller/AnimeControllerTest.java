package com.springboot.animelist.controller;

import com.springboot.animelist.domain.Anime;
import com.springboot.animelist.requests.AnimePatchEpisodeRequestBody;
import com.springboot.animelist.requests.AnimePatchNameRequestBody;
import com.springboot.animelist.requests.AnimePostRequestBody;
import com.springboot.animelist.requests.AnimePutRequestBody;
import com.springboot.animelist.service.AnimeService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;


@ExtendWith(SpringExtension.class)
@DisplayName("test for anime controller")
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;
    @Mock
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnimeToUpdate()));

        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNonPageable())
                .thenReturn(animePage.toList());

        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(animePage.toList().get(0));

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(animePage.toList());

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(animePage.toList().get(0));

        BDDMockito.when(animeServiceMock.replaceName(ArgumentMatchers.any(AnimePatchNameRequestBody.class)))
                        .thenReturn(animePage.toList().get(0));

        BDDMockito.when(animeServiceMock.replaceEpisode(ArgumentMatchers.any(AnimePatchEpisodeRequestBody.class)))
                        .thenReturn(animePage.toList().get(0));

        BDDMockito.when(animeServiceMock.replaceEpisode(ArgumentMatchers.any(AnimePatchEpisodeRequestBody.class)))
                        .thenReturn(animePage.toList().get(0));

        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).remove(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list return list of anime inside of page object when successful")
    void list_ReturnsListOfAnimeInsideOfPageObject_WhenSuccessful(){
        var expectedId = AnimeCreator.createValidAnimeToUpdate().getId();

        Page<Anime> animePage =  animeController.list(null).getBody();

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
    @DisplayName("listAll return list of anime when successful")
    void listAll_ReturnsListOfAnime_WhenSuccessful(){
        var expectedId = AnimeCreator.createValidAnimeToUpdate().getId();

        List<Anime> animes = animeController.listAll().getBody();

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
    @DisplayName("findById return anime when successful")
    void findById_ReturnsListOfAnime_WhenSuccessful(){
        var expectedId = AnimeCreator.createValidAnimeToUpdate().getId();

        Anime animes = animeController.findById(1L).getBody();

        Assertions.assertThat(animes).isNotNull();

        Assertions.assertThat(animes.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName return list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        var expectedId = AnimeCreator.createValidAnimeToUpdate().getId();

        List<Anime> animes = animeController.findByName("anime").getBody();

        Assertions.assertThat(animes).isNotEmpty();

        Assertions.assertThat(animes.get(0).getId())
                .isNotNull()
                .isEqualTo(expectedId);

        Assertions.assertThat(animes.get(0).getName())
                .isNotEmpty();

        Assertions.assertThat(animes.get(0).getEpisode())
                .isNotNull();
    }

    @Test
    @DisplayName("findByName return empty anime list anime is not found")
    void findByName_ReturnsEmptyAnimeList_WhenAnimeIsNotFound(){
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeController.findByName("anime").getBody();

        Assertions.assertThat(animes).isEmpty();
    }


    @Test
    @DisplayName("save persist anime when successful")
    void save_ReturnAnime_WhenSuccess(){
        var expectedId = AnimeCreator.createValidAnimeToUpdate().getId();

        Anime anime = animeController.save(AnimePostRequestBodyCreator.animePostRequestBody()).getBody();

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
        var expectedId = AnimeCreator.createValidAnimeToUpdate().getId();

        Anime anime = animeController.replaceName(AnimePatchNameRequestBodyCreator.animePatchNameRequestBody())
                .getBody();
        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("replaceEpisode update episode of anime when successful")
    void replaceEpisode_ReturnAnime_WhenSuccessful(){
        var expectedId = AnimeCreator.createValidAnimeToUpdate().getId();

        Anime anime = animeController.replaceEpisode(AnimePatchEpisodeRequestBodyCreator.animePatchEpisodeRequestBody())
                .getBody();

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }



    @Test
    @DisplayName("replace update anime when successful")
    void replace_UpdateAnime_WhenSuccessful(){
        ResponseEntity<Void> response = animeController.replace(AnimePutRequestBodyCreator.animePutRequestBody());

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("remove delete anime when successful")
    void remove_DeleteAnime_WhenSuccessful(){
        ResponseEntity<Void> response = animeController.remove(1L);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

    }
}