package csc207.gamecentre.scoreboard;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A Final Score of a finished game
 */
public class FinalScore implements Comparable<FinalScore>, Serializable {

    /**
     * Description of whose final score this is
     */
    private String description;

    /**
     * Score of the finished gam
     */
    private int score;

    /**
     * Creates a new FinalScore
     *
     * @param description who's final score this is
     * @param score       the final score of the game
     */
    public FinalScore(String description, int score) {
        this.description = description;
        this.score = score;
    }

    /**
     * Returns the score
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the description
     *
     * @return the description
     */
    @NonNull
    public String getDescription() {
        return description;
    }

    /**
     * Sets the finished game score
     *
     * @param newScore the final score of a finished game
     */
    public void setScore(int newScore) {
        this.score = newScore;
    }

    /**
     * Makes Final Scores comparable
     *
     * @param finalScore1 the other FinalScore
     * @return an int for comparing
     */
    @Override
    public int compareTo(@NonNull FinalScore finalScore1) {
        if (score < finalScore1.score) {
            return -1;
        } else if (score > finalScore1.score) {
            return 1;
        }
        return 0;
    }
}
