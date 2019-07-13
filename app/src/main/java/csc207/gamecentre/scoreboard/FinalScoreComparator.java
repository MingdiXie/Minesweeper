package csc207.gamecentre.scoreboard;

import java.util.Comparator;

/**
 * A comparator for Final Scores
 */
public class FinalScoreComparator implements Comparator<FinalScore> {
    @Override
    public int compare(FinalScore o1, FinalScore o2) {
        if (o1.getScore() < o2.getScore()) {
            return -1;
        } else if (o1.getScore() > o2.getScore()) {
            return 1;
        }
        return 0;
    }
}
