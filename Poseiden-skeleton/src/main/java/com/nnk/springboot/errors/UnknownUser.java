package com.nnk.springboot.errors;

/**
 * This class is used to define the exception that is thrown when an unknown user is found.
 */
public class UnknownUser extends RuntimeException {
    public UnknownUser(final Integer id) {
        super("Unknown user with id: " + id);
    }
}
