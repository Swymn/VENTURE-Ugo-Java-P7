package com.nnk.springboot.errors;

/**
 * UnknownTrade exception is thrown when a trade with a given id is not found.
 */
public class UnknownTrade extends RuntimeException {
    public UnknownTrade(final Integer id) {
        super("Unknown trade: " + id);
    }
}
