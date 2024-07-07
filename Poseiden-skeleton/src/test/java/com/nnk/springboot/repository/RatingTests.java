package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

	@Test
	void ratingTest() {
		var rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		rating = ratingRepository.save(rating);
		Assertions.assertNotNull(rating.getId());
        Assertions.assertEquals(10, (int) rating.getOrderNumber());

		// Update
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
        Assertions.assertEquals(20, (int) rating.getOrderNumber());

		// Find
		final var listResult = ratingRepository.findAll();
        Assertions.assertFalse(listResult.isEmpty());

		// Delete
		final var id = rating.getId();
		ratingRepository.delete(rating);
		final var ratingList = ratingRepository.findById(id);
		Assertions.assertFalse(ratingList.isPresent());
	}
}
