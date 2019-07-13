package csc207.gamecentre.scoreboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import csc207.gamecentre.R;


/**
 * An adapter to help display the scoreboard
 * Parts of code from http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class ScoreboardAdapter extends ArrayAdapter<FinalScore> {

    /**
     * An object to hold what is to be displayed
     */
    private static class ViewHolder {
        /**
         * The description of the object
         */
        TextView description;
        /**
         * The score of the object
         */
        TextView score;
        /**
         * The ranking of the object
         */
        TextView ranking;
    }

    /**
     * Creates a new ScoreboardAdapter
     *
     * @param context     the current context
     * @param finalScores the list of top scores of finished games
     */
    ScoreboardAdapter(Context context, ArrayList<FinalScore> finalScores) {
        super(context, R.layout.row_item, finalScores);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        // Get the data item for this position
        FinalScore finalScore = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.description = convertView.findViewById(R.id.Description);
            viewHolder.score = convertView.findViewById(R.id.Score);
            viewHolder.ranking = convertView.findViewById(R.id.Ranking);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert finalScore != null;
        viewHolder.description.setText(finalScore.getDescription());
        viewHolder.score.setText(String.valueOf(finalScore.getScore()));
        viewHolder.ranking.setText(String.valueOf(position + 1));
        return convertView;
    }
}

