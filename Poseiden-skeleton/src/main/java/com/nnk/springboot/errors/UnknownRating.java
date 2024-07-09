package com.nnk.springboot.errors;

/**
 * Exception thrown when a rating with a given id is not found.
 */
public class UnknownRating extends RuntimeException {
    public UnknownRating(Integer id) {
        super("Unknown rating with id: " + id);
    }
}
