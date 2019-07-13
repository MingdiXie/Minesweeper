package csc207.gamecentre.scoreboard;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;

/**
 * tests for the class ScoreBoard
 */
public class ScoreboardTest {

    /**
     * a Scoreboard
     */
    private Scoreboard scoreboard;

    /**
     * An ArrayList containing Final Scores
     */
    private ArrayList<FinalScore> list;

    /**
     * a iteration of a final score
     */
    private FinalScore s1;

    /**
     * a iteration of a final score
     */
    private FinalScore s2;

    /**
     * a iteration of a final score
     */
    private FinalScore s3;

    /**
     * a iteration of a final score
     */
    private FinalScore s4;

    /**
     * setting up the scoreboard
     */
    private void setUp() {
        scoreboard = new Scoreboard();
        list = new ArrayList<>();
        s1 = new FinalScore("user1", 64);
        s2 = new FinalScore("user2", 30);
        s3 = new FinalScore("sliding tiles", 90);
        s4 = new FinalScore("user3", 30);
        list.add(s3);
        list.add(s1);
        list.add(s1);
        list.add(s4);
        list.add(s2);
    }

    /**
     * testing the update method to put the top score first
     */
    @Test
    public void testUpdate() {
        setUp();
        scoreboard.update(s1);
        scoreboard.update(s2);
        scoreboard.update(s4);
        scoreboard.update(s3);
        scoreboard.update(s1);

        assertArrayEquals(list.toArray(), scoreboard.getTopScores().toArray());


    }
}
