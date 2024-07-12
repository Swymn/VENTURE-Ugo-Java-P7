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
    public BidList saveBidList(final BidList bidList) {
        return bidListRepository.save(bidList);
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public List<BidList> findAllBidList() {
        return bidListRepository.findAll();
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
    public BidList updateBidList(final BidList bidList) throws UnknownBidList {
        if (bidListRepository.existsById(bidList.getBidListId())) {
            return bidListRepository.save(bidList);
        } else {
            throw new UnknownBidList(bidList.getBidListId());
        }
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void deleteBidList(final Integer id) {
        bidListRepository.deleteById(id);
    }
}
