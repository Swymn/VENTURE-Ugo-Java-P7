package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.errors.UnknownRating;

import java.util.List;
import java.util.Optional;

/**
 * Interface for the Rating service. It provides the basic methods to interact with the Rating table.
 */
public interface RatingService {

    /**
     * Save a new Rating in the database.
     * @param rating The Rating to save.
     */
    void saveRating(Rating rating);

    /**
     * Get all the Ratings from the database.
     * @return A list of all the Ratings.
     */
    List<Rating> findAllRating();

    /**
     * Find a Rating by its id.
     * @param id The id of the Rating to find.
     * @return The Rating with the given id, or an empty Optional if it was not found.
     */
    Optional<Rating> findRatingById(Integer id);

    /**
     * Update a Rating in the database.
     * @param rating The Rating to update.
     * @throws UnknownRating If the Rating to update was not found in the database.
     */
    void updateRating(Rating rating) throws UnknownRating;

    /**
     * Delete a Rating from the database.
     * @param id The id of the Rating to delete.
     * @throws UnknownRating If the Rating to delete was not found in the database.
     */
    void deleteRatingById(Integer id) throws UnknownRating;
}
