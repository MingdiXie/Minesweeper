package csc207.gamecentre.scoreboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import csc207.gamecentre.R;

/**
 * The screen that displays a scoreboard
 */
public class ScoreboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_board);

        Intent intent = getIntent();
        Scoreboard currScoreboard = (Scoreboard) intent.getSerializableExtra("Current_Scoreboard");

        ArrayList<FinalScore> scores = currScoreboard.getTopScores();

        ScoreboardAdapter adapter = new ScoreboardAdapter(getApplicationContext(), scores);
        ListView listView = findViewById(R.id.List);
        listView.setAdapter(adapter);
    }
}
