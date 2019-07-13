package csc207.gamecentre.scoreboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A scoreboard of finished games
 */
public class Scoreboard implements Serializable {

    /**
     * The top scores kept in the scoreboard
     */
    private ArrayList<FinalScore> topScores;

    /**
     * Creates a new scoreboard
     */
    public Scoreboard() {
        this.topScores = new ArrayList<>();
    }

    /**
     * Returns the top scores in the scoreboard
     *
     * @return the top scores
     */
    ArrayList<FinalScore> getTopScores() {
        return topScores;
    }

    /**
     * Updates the scoreboard with a new final score
     *
     * @param finalScore the final score to be added to the scoreboard
     */
    public void update(FinalScore finalScore) {
        topScores.add(finalScore);
        FinalScoreComparator newComparator = new FinalScoreComparator();
        topScores.sort(newComparator);
        Collections.reverse(topScores);
    }
}
