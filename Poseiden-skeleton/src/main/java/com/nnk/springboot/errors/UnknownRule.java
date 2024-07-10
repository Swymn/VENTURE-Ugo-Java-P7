package com.nnk.springboot.errors;

/**
 * UnknownRule is an exception that is thrown when a rule with a given id is not found.
 */
public class UnknownRule extends RuntimeException {
    public UnknownRule(Integer id) {
        super("Unknown rule with id: " + id);
    }
}
