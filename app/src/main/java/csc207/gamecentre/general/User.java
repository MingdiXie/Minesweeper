package csc207.gamecentre.general;


import java.io.Serializable;
import java.util.HashMap;

import csc207.gamecentre.memorypuzzle.MPStartingActivity;
import csc207.gamecentre.minesweeper.MSStartingActivity;
import csc207.gamecentre.scoreboard.Scoreboard;
import csc207.gamecentre.slidingtiles.STStartingActivity;

/*
 * A user with an account for this app
 */
public class User implements Serializable {

    /**
     * The first name of the User
     */
    private String firstName;

    /**
     * The last name of the User
     */
    private String lastName;

    /**
     * The unique username of the User
     */
    private String username;

    /**
     * The password of the User
     */
    private String password;

    /**
     * The previous saved files of games played by the User
     */
    private HashMap<String, String> prevSavedFiles = new HashMap<>();

    /**
     * The user's scoreboard
     */
    private Scoreboard scoreboard;

    /**
     * Creates a new User
     *
     * @param firstName the User's first name
     * @param lastName  the User's last name
     * @param username  the User's username
     * @param password  the User's password
     */
    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.scoreboard = new Scoreboard();
        this.prevSavedFiles.put(STStartingActivity.GAME_NAME, username + "_ST_save_file.ser");
        this.prevSavedFiles.put(MPStartingActivity.GAME_NAME, username + "_MP_save_file.ser");
        this.prevSavedFiles.put(MSStartingActivity.GAME_NAME, username + "_MS_save_file.ser");
    }

    /**
     * Returns the User's first name
     *
     * @return the first name of the User
     */
    String getFirstName() {
        return firstName;
    }

    /**
     * Returns the User's last name
     *
     * @return the last name of the User
     */
    String getLastName() {
        return lastName;
    }

    /**
     * Return the username of the User
     *
     * @return the username of the User
     */
    public String getUsername() {
        return username;
    }

    /**
     * Return the password of the User
     *
     * @return the password of the User
     */
    String getPassword() {
        return password;
    }

    /**
     * Returns the name of the previous saved file for a game
     *
     * @param game The game that we want the previous saved file
     * @return the previous saved file of game game
     */
    public String getPrevSavedFiles(String game) {
        return prevSavedFiles.get(game);
    }

    /**
     * Returns the User's scoreboard
     *
     * @return the User's scoreboard
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
