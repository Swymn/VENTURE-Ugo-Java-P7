package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	void tradeTest() {
		var trade = new Trade("Trade Account", "Type");

		// Save
		trade = tradeRepository.save(trade);
		Assertions.assertNotNull(trade.getTradeId());
        Assertions.assertEquals("Trade Account", trade.getAccount());

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
        Assertions.assertEquals("Trade Account Update", trade.getAccount());

		// Find
		final var listResult = tradeRepository.findAll();
        Assertions.assertFalse(listResult.isEmpty());

		// Delete
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		final var tradeList = tradeRepository.findById(id);
		Assertions.assertFalse(tradeList.isPresent());
	}
}
