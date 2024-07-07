package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.errors.UnknownBidList;

import java.util.Optional;

/**
 * BidList service, allows to perform CRUD operations on BidList
 */
public interface BidListService {

    /**
     * Save a new BidList
     * @param bidList the BidList to save
     */
    void saveBidList(final BidList bidList);

    /**
     * Update an existing BidList
     * @param bidList the BidList to update
     */
    void updateBidList(final BidList bidList) throws UnknownBidList;

    /**
     * Delete an existing BidList
     * @param id the id of the BidList to delete
     */
    void deleteBidList(final Integer id);

    /**
     * Find all BidList
     * @param id the id of the BidList to delete
     */
    Optional<BidList> findBidListById(final Integer id);
}
