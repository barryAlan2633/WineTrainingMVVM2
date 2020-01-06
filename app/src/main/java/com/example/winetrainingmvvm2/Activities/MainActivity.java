package com.example.winetrainingmvvm2.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.winetrainingmvvm2.R;
import com.example.winetrainingmvvm2.ViewModels.ViewModel2;

import static com.example.winetrainingmvvm2.Constants.Constants.CHOOSING_CATEGORY;
import static com.example.winetrainingmvvm2.Constants.Constants.USER_STATE;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ViewModel2 mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnHighScores = findViewById(R.id.btn_highScores);
        Button btnWineList = findViewById(R.id.btn_editWine);
        Button btnQuestionList = findViewById(R.id.btn_editQuestions);
        Button btnPlay = findViewById(R.id.btn_play);

        initViewModel();

        btnHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HighScoresActivity.class);
                mViewModel.getGameState().set(USER_STATE,CHOOSING_CATEGORY);
                startActivity(intent);
            }
        });

        btnWineList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                mViewModel.getGameState().set(USER_STATE,CHOOSING_CATEGORY);
                startActivity(intent);
            }
        });

        btnQuestionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                mViewModel.getGameState().set(USER_STATE,CHOOSING_CATEGORY);
                startActivity(intent);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity2.class);
                mViewModel.getGameState().set(USER_STATE,CHOOSING_CATEGORY);
                startActivity(intent);
            }
        });

        btnPlay.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_btn_play));

    }

    @Override
    public void onBackPressed() {
        exitDialog();
    }

    //Initialize
    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ViewModel2.class);
    }


    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_are_you_sure, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvQuestion = alertDialog.findViewById(R.id.tv_are_you_sure_dialog_question);
        Button btnYes = alertDialog.findViewById(R.id.btn_yes);
        Button btnCancel = alertDialog.findViewById(R.id.btn_cancel);

        tvQuestion.setText("Are you sure you want to close the app?");

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finishAndRemoveTask();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}
