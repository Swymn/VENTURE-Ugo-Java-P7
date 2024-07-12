package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.errors.UnknownBidList;

import java.util.List;
import java.util.Optional;

/**
 * BidList service, allows to perform CRUD operations on BidList
 */
public interface BidListService {

    /**
     * Save a new BidList
     * @param bidList the BidList to save
     */
    BidList saveBidList(final BidList bidList);

    /**
     * Find all BidList
     * @return a list of all BidList
     */
    List<BidList> findAllBidList();

    /**
     * Find by id BidList
     * @param id the id of the BidList to get
     */
    Optional<BidList> findBidListById(final Integer id);

    /**
     * Update an existing BidList
     * @param bidList the BidList to update
     */
    BidList updateBidList(final BidList bidList) throws UnknownBidList;

    /**
     * Delete an existing BidList
     * @param id the id of the BidList to delete
     */
    void deleteBidList(final Integer id);
}
