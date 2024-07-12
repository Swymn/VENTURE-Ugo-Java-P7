package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.errors.UnknownRating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(final RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rating saveRating(final Rating rating) {
        return ratingRepository.save(rating);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Rating> findAllRating() {
        return ratingRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Rating> findRatingById(final Integer id) {
        return ratingRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rating updateRating(final Rating rating) throws UnknownRating {
        if (ratingRepository.existsById(rating.getId())) {
            return ratingRepository.save(rating);
        } else {
            throw new UnknownRating(rating.getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteRatingById(final Integer id) throws UnknownRating {
        if (ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
        } else {
            throw new UnknownRating(id);
        }
    }
}
