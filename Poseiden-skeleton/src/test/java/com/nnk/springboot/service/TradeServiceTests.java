package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.errors.UnknownTrade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.impl.TradeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
class TradeServiceTests {

    @Autowired
    private TradeService tradeService;

    @MockBean
    private TradeRepository tradeRepository;

    @BeforeEach
    void setUp() {
        tradeRepository = Mockito.mock(TradeRepository.class);
        tradeService = new TradeServiceImpl(tradeRepository);
    }

    @Test
    void saveTrade_shouldSaveTrade_newTrade() {
        // GIVEN a new trade
        final var trade = new Trade();

        // WHEN saving the trade
        tradeService.saveTrade(trade);

        // THEN the trade is saved
        Mockito.verify(tradeRepository).save(trade);
    }

    @Test
    void findAllTrades_shouldReturnAllTrades() {
        // GIVEN a trade service
        // WHEN finding all trades
        tradeService.findAllTrades();

        // THEN all trades are returned
        Mockito.verify(tradeRepository).findAll();
    }

    @Test
    void findTradeById_shouldReturnTradeById_existingTrade() {
        // GIVEN a trade id
        final var id = 1;

        // WHEN finding a trade by its id
        Mockito.when(tradeRepository.findById(id)).thenReturn(Optional.of(new Trade()));
        final var trade = tradeService.findTradeById(id);

        // THEN the trade with the given id is returned
        Mockito.verify(tradeRepository).findById(id);
        // AND the trade is returned
        Assertions.assertTrue(trade.isPresent());
    }

    @Test
    void findTradeById_shouldReturnEmpty_missingTrade() {
        // GIVEN a trade id
        final var id = 1;

        // WHEN finding a trade by its id
        Mockito.when(tradeRepository.findById(id)).thenReturn(Optional.empty());
        final var trade = tradeService.findTradeById(id);

        // THEN the trade with the given id is returned
        Mockito.verify(tradeRepository).findById(id);
        // AND the trade is returned
        Assertions.assertTrue(trade.isEmpty());
    }

    @Test
    void updateTrade_shouldUpdateTrade_existingTrade() {
        // GIVEN an existing trade
        final var trade = new Trade();

        // WHEN updating the trade
        Mockito.when(tradeRepository.existsById(trade.getTradeId())).thenReturn(true);
        tradeService.updateTrade(trade);

        // THEN the trade is updated
        Mockito.verify(tradeRepository).save(trade);
    }

    @Test
    void updateTrade_shouldThrowUnknownTrade_missingTrade() {
        // GIVEN a missing trade
        final var trade = new Trade();

        // WHEN updating the trade
        Mockito.when(tradeRepository.existsById(trade.getTradeId())).thenReturn(false);

        // THEN an UnknownTrade exception is thrown
        Assertions.assertThrows(UnknownTrade.class, () -> tradeService.updateTrade(trade));
    }

    @Test
    void deleteTrade_shouldDeleteTrade_existingTrade() {
        // GIVEN an existing trade id
        final var id = 1;

        // WHEN deleting the trade
        Mockito.when(tradeRepository.existsById(id)).thenReturn(true);
        tradeService.deleteTrade(id);

        // THEN the trade is deleted
        Mockito.verify(tradeRepository).deleteById(id);
    }

    @Test
    void deleteTrade_shouldThrowUnknownTrade_missingTrade() {
        // GIVEN a missing trade id
        final var id = 1;

        // WHEN deleting the trade
        Mockito.when(tradeRepository.existsById(id)).thenReturn(false);

        // THEN an UnknownTrade exception is thrown
        Assertions.assertThrows(UnknownTrade.class, () -> tradeService.deleteTrade(id));
    }
}
