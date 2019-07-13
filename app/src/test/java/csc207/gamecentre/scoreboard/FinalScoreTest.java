package csc207.gamecentre.scoreboard;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * tests for the class FinalScore
 */
public class FinalScoreTest {

    /**
     * testing the compareTo method
     */
    @Test
    public void testCompareTo() {
        FinalScore s1 = new FinalScore("user1", 64);
        FinalScore s2 = new FinalScore("user2", 30);
        FinalScore s3 = new FinalScore("sliding tiles", 90);
        FinalScore s4 = new FinalScore("user3", 30);
        assertEquals(1, s1.compareTo(s2));
        assertEquals(-1, s1.compareTo(s3));
        assertEquals(0, s2.compareTo(s4));

    }

    /**
     * Testing the final score comparator
     */
    @Test
    public void testFinalScoreComparator() {
        FinalScoreComparator com = new FinalScoreComparator();
        FinalScore s2 = new FinalScore("user2", 30);
        FinalScore s3 = new FinalScore("sliding tiles", 90);
        FinalScore s4 = new FinalScore("user3", 30);
        assertEquals(0, com.compare(s2, s4));
        assertEquals(-1, com.compare(s2, s3));
        assertEquals(1, com.compare(s3, s2));

    }

    /**
     * testing the getter and setters in final score
     */
    @Test
    public void testGetterAndSetter() {
        FinalScore f1 = new FinalScore("user1", 90);
        assertEquals("user1", f1.getDescription());
        f1.setScore(100);
        assertEquals(100, f1.getScore());


    }
}
