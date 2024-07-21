package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.errors.UnknownRating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.impl.RatingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RatingServiceTests {

    private RatingService ratingService;
    private RatingRepository ratingRepository;

    @BeforeEach
    public void setUp() {
        ratingRepository = Mockito.mock(RatingRepository.class);
        ratingService = new RatingServiceImpl(ratingRepository);
    }

    @Test
    void saveRating_shouldSaveRating_newRating() {
        // GIVEN a new rating
        final var rating = new Rating("Moodys", "SandP", "Fitch", 10);

        // WHEN saving the rating
        ratingService.saveRating(rating);

        // THEN the rating is saved
        Mockito.verify(ratingRepository, Mockito.times(1)).save(rating);
    }

    @Test
    void getAllRating_shouldReturnAllRatings_existingRatings() {
        // GIVEN a rating service
        // WHEN getting all ratings
        ratingService.findAllRating();

        // THEN all ratings are returned
        Mockito.verify(ratingRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findRatingById_shouldReturnRatingWithGivenId_existingRating() {
        // GIVEN a rating service and an id
        final var id = 1;

        // WHEN finding a rating by its id
        Mockito.when(ratingRepository.findById(id)).thenReturn(java.util.Optional.of(new Rating("Moodys", "SandP", "Fitch", 10)));
        final var rating = ratingService.findRatingById(id);

        // THEN the rating with the given id is returned
        Mockito.verify(ratingRepository, Mockito.times(1)).findById(1);
        // AND the rating isn't empty
        Assertions.assertTrue(rating.isPresent());
    }

    @Test
    void findRatingById_shouldReturnEmptyOptional_missingRating() {
        // GIVEN a rating service and an id
        final var id = 1;

        // WHEN finding a rating by its id
        Mockito.when(ratingRepository.findById(id)).thenReturn(java.util.Optional.empty());
        final var rating = ratingService.findRatingById(id);

        // THEN the rating with the given id is returned
        Mockito.verify(ratingRepository, Mockito.times(1)).findById(1);
        // AND the rating is empty
        Assertions.assertTrue(rating.isEmpty());
    }

    @Test
    void updateRating_shouldUpdateRating_existingRating() {
        // GIVEN a rating service and a rating
        final var rating = new Rating("Moodys", "SandP", "Fitch", 10);

        // WHEN updating the rating
        Mockito.when(ratingRepository.existsById(rating.getId())).thenReturn(true);
        ratingService.updateRating(rating);

        // THEN the rating is updated
        Mockito.verify(ratingRepository, Mockito.times(1)).save(rating);
    }

    @Test
    void updateRating_shouldThrowUnknownRating_missingRating() {
        // GIVEN a rating service and a rating
        final var rating = new Rating("Moodys", "SandP", "Fitch", 10);

        // WHEN updating the rating
        Mockito.when(ratingRepository.existsById(rating.getId())).thenReturn(false);

        // THEN an UnknownRating exception is thrown
        Assertions.assertThrows(UnknownRating.class, () -> ratingService.updateRating(rating));
    }

    @Test
    void deleteRating_shouldDeleteRating_existingRating() {
        // GIVEN a rating service and an id
        final var id = 1;

        // WHEN deleting the rating
        Mockito.when(ratingRepository.existsById(id)).thenReturn(true);
        ratingService.deleteRatingById(id);

        // THEN the rating is deleted
        Mockito.verify(ratingRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void deleteRating_shouldThrowUnknownRating_missingRating() {
        // GIVEN a rating service and an id
        final var id = 1;

        // WHEN deleting the rating
        Mockito.when(ratingRepository.existsById(id)).thenReturn(false);

        // THEN an UnknownRating exception is thrown
        Assertions.assertThrows(UnknownRating.class, () -> ratingService.deleteRatingById(id));
    }
}
