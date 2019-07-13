package csc207.gamecentre.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A singleton User Manager to manage all the users on the app
 */
public class UserManager implements Serializable {

    /**
     * The single User Manager
     */
    private static UserManager single = null;

    /**
     * The list of all Users who have an account on this app
     */
    private List<User> user_list;

    /**
     * Creates a new User Manager
     */
    private UserManager() {
        user_list = new ArrayList<>();
    }

    /**
     * Returns the single User Manager
     *
     * @return the single User Manager
     */
    public static UserManager getUserManager() {
        if (single == null) {
            single = new UserManager();
        }
        return single;
    }

    /**
     * Returns if there is already a User with username checkUsername
     *
     * @param checkUsername the username to check if taken
     * @return if the username is already in use
     */
    boolean isUsernameTaken(String checkUsername) {
        boolean usernameExists = false;
        for (User user : user_list) {
            if (user.getUsername().equals(checkUsername)) {
                usernameExists = true;
            }
        }
        return usernameExists;
    }

    /**
     * Adds newUser to the list of all Users with an account on this app
     *
     * @param newUser the new User
     */
    public void addNewUser(User newUser) {
        user_list.add(newUser);
    }

    /**
     * Returns the User with matching username and password, or null if there is no User like that
     *
     * @param username the username
     * @param password the password
     * @return the User with matching username and password, or null if there is none
     */
    User signIn(String username, String password) {
        for (User user : user_list) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Updates the User data to its new data in updatedUser
     *
     * @param updatedUser the User with its information updated
     */
    void updateUser(User updatedUser) {
        int index = 0;
        for (User user : user_list) {
            if (user.getUsername().equals(updatedUser.getUsername())) {
                index = user_list.indexOf(user);
            }
        }
        user_list.set(index, updatedUser);
    }

    /**
     * Clears the current list of users
     */
    void clearUsers() {
        user_list = new ArrayList<>();
    }
}
