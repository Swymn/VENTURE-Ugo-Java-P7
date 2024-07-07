package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.errors.UnknownBidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;

    public BidListServiceImpl(final BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void saveBidList(final BidList bidList) {
        bidListRepository.save(bidList);
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void updateBidList(final BidList bidList) {
        final var existingBidList = bidListRepository.findById(bidList.getBidListId());
        if (existingBidList.isEmpty()) {
            throw new UnknownBidList(bidList.getBidListId());
        }
        bidListRepository.save(bidList);
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void deleteBidList(final Integer id) {
        bidListRepository.deleteById(id);
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public Optional<BidList> findBidListById(final Integer id) {
        return bidListRepository.findById(id);
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public List<BidList> findAllBidList() {
        return bidListRepository.findAll();
    }
}
