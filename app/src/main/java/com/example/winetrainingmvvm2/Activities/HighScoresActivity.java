package com.example.winetrainingmvvm2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.winetrainingmvvm2.Models.Score;
import com.example.winetrainingmvvm2.Models.Wine;
import com.example.winetrainingmvvm2.R;
import com.example.winetrainingmvvm2.ViewModels.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HighScoresActivity extends AppCompatActivity {
    private static final String TAG = "HighScoresActivity";
    private List<Score> mAllScores = new ArrayList<>();
    private ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);


        initViewModel();


    }

    private void initViewModel() {

        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);


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

        Toast.makeText(this, "scores available = " + mAllScores.size(), Toast.LENGTH_SHORT).show();

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
            Log.d(TAG, "fillScoresTable: ScoresList size = " + mAllScores.size());

            for (Score score : mAllScores) {

                if (score.getType() == PlayActivity.NAMES) {
                    Log.d(TAG, "fillScoresTable1: " + score.toString());

                    if (score.getPlace() == 1) {

                        Log.d(TAG, "fillScoresTable2: " + score.toString());
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
