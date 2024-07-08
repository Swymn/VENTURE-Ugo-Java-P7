package com.nnk.springboot.errors;

/**
 * Exception thrown when a curve point with a given id is not found.
 */
public class UnknownCurvePoint extends RuntimeException {
    public UnknownCurvePoint(final Integer id) {
        super("Unknown curve point with id: " + id);
    }
}
