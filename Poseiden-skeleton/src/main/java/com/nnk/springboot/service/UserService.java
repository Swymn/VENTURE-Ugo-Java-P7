package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.errors.UnknownUser;

import java.util.List;
import java.util.Optional;

/**
 * This interface is used to define the methods that will be used in the UserServiceImpl class.
 */
public interface UserService {

    /***
     * This method is used to save a user.
     * @param user The user to save.
     */
    User saveUser(User user);

    /**
     * This method is used to find all users.
     * @return The list of all users.
     */
    List<User> findAllUsers();

    /**
     * This method is used to find a user by its id.
     * @param id The id of the user to find.
     * @return The user found.
     */
    Optional<User> findUserById(Integer id);

    /**
     * This method is used to update a user.
     * @param user The user to update.
     * @throws UnknownUser The exception thrown when the user is unknown.
     */
    User updateUser(User user) throws UnknownUser;

    /**
     * This method is used to delete a user by its id.
     * @param id The id of the user to delete.
     * @throws UnknownUser The exception thrown when the user is unknown.
     */
    void deleteUserById(Integer id) throws UnknownUser;
}
