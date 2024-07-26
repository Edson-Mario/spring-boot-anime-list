package com.springboot.animelist.repository;

import com.springboot.animelist.domain.Anime;
import com.springboot.animelist.util.AnimeCreator;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("test for anime repository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("save persists anime when successful")
    void save_PersistsAnime_WhenSuccessful(){
        Anime anime = AnimeCreator.createAnimeToBeSave();

        Anime animeSaved = this.animeRepository.save(anime);

        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();

        Assertions.assertThat(animeSaved.getName())
                .isNotEmpty()
                .isEqualTo(anime.getName());

        Assertions.assertThat(animeSaved.getEpisode())
                .isNotNull()
                .isEqualTo(anime.getEpisode());
    }

    @Test
    @DisplayName("save updates anime when successful")
    void save_UpdatesAnime_WhenSuccessful(){
        Anime anime = AnimeCreator.createAnimeToBeSave();
        Anime animeSaved = this.animeRepository.save(anime);

        Anime animeToUpdate = AnimeCreator.createValidAnimeToUpdate();
        animeToUpdate.setId(animeSaved.getId());
        Anime animeUpdated = this.animeRepository.save(animeToUpdate);

        Assertions.assertThat(animeUpdated).isNotNull();

        Assertions.assertThat(animeUpdated.getId())
                .isNotNull()
                .isEqualTo(1L);

        Assertions.assertThat(animeUpdated.getName())
                .isNotEmpty()
                .isNotEqualTo(AnimeCreator.createAnimeToBeSave().getName());

        Assertions.assertThat(animeUpdated.getEpisode())
                .isNotNull()
                .isEqualTo(AnimeCreator.createAnimeToBeSave().getEpisode());
    }

    @Test
    @DisplayName("save throws ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty(){
        Anime anime = AnimeCreator.createAnimeWithEmptyName();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(anime))
                .withMessageContaining("Anime name cannot be empty");

    }

    @Test
    @DisplayName("save throws ConstraintViolationException when episode is null")
    void save_ThrowsConstraintViolationException_WhenEpisodeIsNull(){
        Anime anime = AnimeCreator.createAnimeWithNullEpisode();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(anime))
                .withMessageContaining("Anime episode cannot be null");

    }

    @Test
    @DisplayName("findById returns anime list when successful")
    void findByName_ReturnsAnimeList_WhenSuccess(){
        Anime anime = AnimeCreator.createAnimeToBeSave();
        Anime animeSaved = this.animeRepository.save(anime);

        List<Anime> animes = this.animeRepository.findByName(anime.getName());

        Assertions.assertThat(animes)
                .isNotEmpty()
                .hasSize(1)
                .contains(animeSaved);

        Assertions.assertThat(animes.get(0).getId())
                .isNotNull()
                .isEqualTo(1L);

        Assertions.assertThat(animes.get(0).getName())
                .isNotEmpty()
                .isEqualTo(anime.getName());

        Assertions.assertThat(animes.get(0).getEpisode())
                .isNotNull()
                .isEqualTo(anime.getEpisode());
    }

    @Test
    @DisplayName("findById returns empty list when anime is not found")
    void findById_ReturnsEmptyList_WhenAnimeIsNotFound(){
        Anime anime = AnimeCreator.createAnimeToBeSave();
        this.animeRepository.save(anime);

        List<Anime> animes = this.animeRepository.findByName("One Peace");

        Assertions.assertThat(animes)
                .isEmpty();
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Anime anime = AnimeCreator.createAnimeToBeSave();
        Anime animeSaved = this.animeRepository.save(anime);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional)
                .isEmpty();
    }
}