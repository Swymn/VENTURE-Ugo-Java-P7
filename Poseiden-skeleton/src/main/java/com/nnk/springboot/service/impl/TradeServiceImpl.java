package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.errors.UnknownTrade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Autowired
    public TradeServiceImpl(final TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveTrade(final Trade trade) {
        tradeRepository.save(trade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Trade> findAllTrades() {
        return tradeRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Trade> findTradeById(Integer id) {
        return tradeRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTrade(final Trade trade) throws UnknownTrade {
        if (tradeRepository.existsById(trade.getTradeId())) {
            tradeRepository.save(trade);
        } else {
            throw new UnknownTrade(trade.getTradeId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTrade(final Integer id) throws UnknownTrade {
        if (tradeRepository.existsById(id)) {
            tradeRepository.deleteById(id);
        } else {
            throw new UnknownTrade(id);
        }
    }
}
