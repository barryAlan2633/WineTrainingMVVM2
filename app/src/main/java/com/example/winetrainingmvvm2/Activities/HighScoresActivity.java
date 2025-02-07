package com.example.winetrainingmvvm2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.winetrainingmvvm2.Models.Score;
import com.example.winetrainingmvvm2.R;
import com.example.winetrainingmvvm2.ViewModels.ViewModel2;

import java.util.ArrayList;
import java.util.List;

import static com.example.winetrainingmvvm2.Constants.Constants.NAMES;

public class HighScoresActivity extends AppCompatActivity {
    private static final String TAG = "HighScoresActivity";
    private List<Score> mAllScores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        initViewModel();
    }

    private void initViewModel() {
        ViewModel2 mViewModel = ViewModelProviders.of(this).get(ViewModel2.class);
        mViewModel.getAllScores().observe(this, new Observer<List<Score>>() {
            @Override
            public void onChanged(List<Score> scores) {
                if (scores != null) {
                    mAllScores = scores;
                    printScore();
                    fillScoresTable();
                }
            }
        });
    }

    private void printScore() {
        Log.d(TAG, "onClick: THE NUMBER OF SCORES IS " + mAllScores.size() + "+++++++++++++++++++++++++++++++++");
    }

    private void fillScoresTable() {
        TextView tvFirstName = findViewById(R.id.tv_first_name);
        TextView tvSecondName = findViewById(R.id.tv_second_name);
        TextView tvThirdName = findViewById(R.id.tv_third_name);
        TextView tvFirstScore = findViewById(R.id.tv_first_score);
        TextView tvSecondScore = findViewById(R.id.tv_second_score);
        TextView tvThirdScore = findViewById(R.id.tv_third_score);

        if (!mAllScores.isEmpty()) {
            for (Score score : mAllScores) {
                if (score.getType() == NAMES) {
                    if (score.getPlace() == 1) {
                        tvFirstName.setText(score.getName());
                        tvFirstScore.setText(Integer.toString(score.getScore()));
                    }
                    if (score.getPlace() == 2) {
                        tvSecondName.setText(score.getName());
                        tvSecondScore.setText(Integer.toString(score.getScore()));
                    }
                    if (score.getPlace() == 3) {
                        tvThirdName.setText(score.getName());
                        tvThirdScore.setText(Integer.toString(score.getScore()));
                    }
                }
            }
        } else {
            Log.d(TAG, "fillScoresTable: Score list is empty. ScoresList size = " + mAllScores.size());
        }
    }
}
