package com.nnk.springboot.repository;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BidTests {

	@Autowired
	private BidListRepository bidListRepository;

	@Test
	void bidListTest() {
		var bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		bid = bidListRepository.save(bid);
		Assertions.assertNotNull(bid.getBidListId());
		Assertions.assertEquals(10d, bid.getBidQuantity(), 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		Assertions.assertEquals(20d, bid.getBidQuantity(), 20d);

		// Find
		final var listResult = bidListRepository.findAll();
        Assertions.assertFalse(listResult.isEmpty());

		// Delete
		final var id = bid.getBidListId();
		bidListRepository.delete(bid);
		final var bidList = bidListRepository.findById(id);
		Assertions.assertFalse(bidList.isPresent());
	}
}
