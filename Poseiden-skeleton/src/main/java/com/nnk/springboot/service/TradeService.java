package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.errors.UnknownTrade;

import java.util.List;
import java.util.Optional;

/**
 * TradeService interface, it contains all methods to interact with the Trade table.
 */
public interface TradeService {

    /**
     * Save a new trade.
     * @param trade the trade to save
     */
    void saveTrade(Trade trade);

    /**
     * Find all trades.
     * @return a list of all trades
     */
    List<Trade> findAllTrades();

    /**
     * Find a trade by its id.
     * @param id the id of the trade to find
     * @return the trade with the given id
     */
    Optional<Trade> findTradeById(Integer id);

    /**
     * Update a trade.
     * @param trade the trade to update
     * @throws UnknownTrade if the trade is not found
     */
    void updateTrade(Trade trade) throws UnknownTrade;

    /**
     * Delete a trade.
     * @param id the id of the trade to delete
     * @throws UnknownTrade if the trade is not found
     */
    void deleteTrade(Integer id) throws UnknownTrade;
}
