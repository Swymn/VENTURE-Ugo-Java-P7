package com.nnk.springboot.errors;

/**
 * Exception thrown when a BidList is not found.
 */
public class UnknownBidList extends RuntimeException {
    public UnknownBidList(final Integer id) {
        super("Unknown BidList with id: " + id);
    }
}
