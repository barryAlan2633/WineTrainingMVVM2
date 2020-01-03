package com.example.winetrainingmvvm2.Activities;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.Models.Score;
import com.example.winetrainingmvvm2.Models.Wine;
import com.example.winetrainingmvvm2.R;
import com.example.winetrainingmvvm2.ViewModels.ViewModel2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.winetrainingmvvm2.Constants.constants.ALL;
import static com.example.winetrainingmvvm2.Constants.constants.CATEGORIES;
import static com.example.winetrainingmvvm2.Constants.constants.FALSE;
import static com.example.winetrainingmvvm2.Constants.constants.GREEN;
import static com.example.winetrainingmvvm2.Constants.constants.NAMES;
import static com.example.winetrainingmvvm2.Constants.constants.PRICES;
import static com.example.winetrainingmvvm2.Constants.constants.RED;
import static com.example.winetrainingmvvm2.Constants.constants.TRUE;
import static com.example.winetrainingmvvm2.Constants.constants.WHITE;

public class PlayActivity2 extends AppCompatActivity {
    private static final String TAG = "PlayActivity";
    private ViewModel2 mViewModel;

    private List<Wine> mAllWines;
    private List<Question> mAllQuestions = new ArrayList<>();
    private List<Score> mAllScores = new ArrayList<>();
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Button btnName = findViewById(R.id.btn_play_names);
        Button btnCategories = findViewById(R.id.btn_play_categories);
        Button btnPrices = findViewById(R.id.btn_play_prices);
        Button btnAll = findViewById(R.id.btn_play_all);
        final Button btnAnswer1 = findViewById(R.id.btn_play_answer1);
        final Button btnAnswer2 = findViewById(R.id.btn_play_answer2);
        final Button btnAnswer3 = findViewById(R.id.btn_play_answer3);

        initViewModel();

//        If the rotation was changed and the play activity was re-started
        if (mViewModel.isGameActive()) {
            setGameLayout();
            setQuestionLayout();
            setAnswersLayout();
            setScoreLayout();
            setButtonBackgrounds(btnAnswer1, btnAnswer2, btnAnswer3);
            if(mViewModel.getGameState().get(6) == TRUE){
                exitDialog();
            }
            if(mViewModel.getGameState().get(7) == TRUE){
                scoreAlertDialog();
            }
        }


        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAllWines.isEmpty() && !mAllQuestions.isEmpty()) {
                    mViewModel.setIsGameActive(true);
                    mViewModel.setChosenType(NAMES);
                    mViewModel.generateQuestion();
                    mViewModel.generateAnswerAndChoices();
                    setGameLayout();
                    setQuestionLayout();
                    setAnswersLayout();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(PlayActivity2.this,
                            "You need to have at least three Wines and one question of this type added on your lists",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAllWines.isEmpty() && !mAllQuestions.isEmpty()) {
                    mViewModel.setIsGameActive(true);
                    mViewModel.setChosenType(CATEGORIES);
                    mViewModel.generateQuestion();
                    mViewModel.generateAnswerAndChoices();
                    setGameLayout();
                    setQuestionLayout();
                    setAnswersLayout();

                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(PlayActivity2.this,
                            "You need to have at least three Wines and one question of this type added on your lists",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAllWines.isEmpty() && !mAllQuestions.isEmpty()) {
                    mViewModel.setIsGameActive(true);
                    mViewModel.setChosenType(PRICES);
                    mViewModel.generateQuestion();
                    mViewModel.generateAnswerAndChoices();
                    setGameLayout();
                    setQuestionLayout();
                    setAnswersLayout();

                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(PlayActivity2.this,
                            "You need to have at least three Wines and one question of this type added on your lists",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAllWines.isEmpty() && !mAllQuestions.isEmpty()) {
                    mViewModel.setIsGameActive(true);
                    mViewModel.setChosenType(ALL);
                    mViewModel.generateQuestion();
                    mViewModel.generateAnswerAndChoices();
                    setGameLayout();
                    setQuestionLayout();
                    setAnswersLayout();

                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(PlayActivity2.this,
                            "You need to have at least three Wines and one question of this type added on your lists",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.getGameState().get(3) == TRUE) {
                    countDownTimer.cancel();
                    if (mViewModel.checkAnswer(btnAnswer1.getText().toString())) {
                        mViewModel.getGameState().set(0, GREEN);
                    } else {
                        mViewModel.getGameState().set(0, RED);
                    }
                    setButtonBackgrounds(btnAnswer1, btnAnswer2, btnAnswer3);
                    setScoreLayout();
                    mViewModel.getGameState().set(3, FALSE);

                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        public void run() {
                            showCorrectAnswer();
                        }

                    }, 1000);

                    if (mViewModel.getGameState().get(5) > 0) {
                        h.postDelayed(new Runnable() {
                            public void run() {
                                resetAnswerButtons(btnAnswer1,btnAnswer2,btnAnswer3);
                                mViewModel.generateQuestion();
                                mViewModel.generateAnswerAndChoices();
                                setQuestionLayout();
                                setAnswersLayout();
                            }
                            }, 2000);

                    } else {
                        mViewModel.getGameState().set(5,3);
                        mViewModel.getGameState().set(7,TRUE);
                        scoreAlertDialog();
                    }
                }
            }
        });

        btnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mViewModel.getGameState().get(3) == TRUE) {
                    countDownTimer.cancel();
                    if (mViewModel.checkAnswer(btnAnswer2.getText().toString())) {
                        mViewModel.getGameState().set(1, GREEN);
                    } else {
                        mViewModel.getGameState().set(1, RED);
                    }
                    setButtonBackgrounds(btnAnswer1, btnAnswer2, btnAnswer3);
                    setScoreLayout();
                    mViewModel.getGameState().set(3, FALSE);

                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        public void run() {
                            showCorrectAnswer();
                        }

                    }, 1000);

                    if (mViewModel.getGameState().get(5) > 0) {
                        h.postDelayed(new Runnable() {
                            public void run() {
                                resetAnswerButtons(btnAnswer1,btnAnswer2,btnAnswer3);
                                mViewModel.generateQuestion();
                                mViewModel.generateAnswerAndChoices();
                                setQuestionLayout();
                                setAnswersLayout();
                            }
                        }, 2000);

                    } else {
                        mViewModel.getGameState().set(5,3);
                        mViewModel.getGameState().set(7,TRUE);
                        scoreAlertDialog();
                    }

                }
            }
        });

        btnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.getGameState().get(3) == TRUE) {
                    countDownTimer.cancel();
                    if (mViewModel.checkAnswer(btnAnswer3.getText().toString())) {
                        mViewModel.getGameState().set(2, GREEN);
                    } else {
                        mViewModel.getGameState().set(2, RED);
                    }
                    setButtonBackgrounds(btnAnswer1, btnAnswer2, btnAnswer3);
                    setScoreLayout();
                    mViewModel.getGameState().set(3, FALSE);


                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        public void run() {
                            showCorrectAnswer();
                        }

                    }, 1000);

                    if (mViewModel.getGameState().get(5) > 0) {
                        h.postDelayed(new Runnable() {
                            public void run() {
                                resetAnswerButtons(btnAnswer1,btnAnswer2,btnAnswer3);
                                mViewModel.generateQuestion();
                                mViewModel.generateAnswerAndChoices();
                                setQuestionLayout();
                                setAnswersLayout();
                            }
                        }, 2000);

                    } else {
                        mViewModel.getGameState().set(5,3);
                        mViewModel.getGameState().set(7,TRUE);
                        scoreAlertDialog();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        mViewModel.getGameState().set(6,TRUE);
        exitDialog();
    }

    //Initialize
    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ViewModel2.class);

        mViewModel.getAllWines().observe(this, new Observer<List<Wine>>() {
            @Override
            public void onChanged(List<Wine> wines) {
                if (wines != null) {
                    mAllWines = wines;
                    mViewModel.setTODOWines(wines);
                }
            }
        });

        mViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                if (questions != null) {
                    mAllQuestions = questions;
                    mViewModel.setTODOQuestions(questions);
                }
            }
        });

        mViewModel.getAllScores().observe(this, new Observer<List<Score>>() {
            @Override
            public void onChanged(List<Score> scores) {
                if (scores != null) {
                    mAllScores = scores;
                    mViewModel.setTODOScores(scores);
                }
            }
        });
    }

    //Layout Setters
    private void setGameLayout() {
        ConstraintLayout categoriesLayout = findViewById(R.id.layout_categories);
        ConstraintLayout playLayout = findViewById(R.id.layout_play);

        TextView tvMode = findViewById(R.id.tv_mode);

        switch (mViewModel.getChosenType()) {
            case 1:
                tvMode.setText("Names");
                break;
            case 2:
                tvMode.setText("Categories");
                break;
            case 3:
                tvMode.setText("Prices");
                break;
            case 4:
                tvMode.setText("All");
                break;
            default:
                tvMode.setText("ERROR");
        }

        categoriesLayout.setVisibility(View.GONE);
        playLayout.setVisibility(View.VISIBLE);

    }
    private void setQuestionLayout() {
        if (!mViewModel.getSelectedWines().isEmpty()) {
            String currentQuestion = mViewModel.getSelectedQuestion().getQuestion();

            String one, two, three;
            one = currentQuestion.substring(0, currentQuestion.indexOf("#"));
            two = currentQuestion.substring(currentQuestion.indexOf("!") + 1);

            if (currentQuestion.contains("#name")) {
                three = one + mViewModel.getAnswer().getName() + two;
            } else if (currentQuestion.contains("#category!")) {
                three = one + mViewModel.getAnswer().getCategory() + two;
            } else if (currentQuestion.contains("#bottle!")) {
                three = one + mViewModel.getAnswer().getBottlePrice() + two;
            } else if (currentQuestion.contains("#glass!")) {
                three = one + mViewModel.getAnswer().getGlassPrice() + two;
            } else {
                three = one + two;
            }

            TextView tvQuestion = findViewById(R.id.tv_play_question);
            tvQuestion.setText(three);
        } else {
            Log.d(TAG, "setQuestionLayout: ERROR currentWinesList is empty");
        }
    }
    private void setAnswersLayout() {
        Button btnAnswer1 = findViewById(R.id.btn_play_answer1);
        Button btnAnswer2 = findViewById(R.id.btn_play_answer2);
        Button btnAnswer3 = findViewById(R.id.btn_play_answer3);

        switch (mViewModel.getSelectedQuestion().getType()) {
            case "name":
                btnAnswer1.setText(mViewModel.getSelectedWines().get(0).getName());
                btnAnswer2.setText(mViewModel.getSelectedWines().get(1).getName());
                btnAnswer3.setText(mViewModel.getSelectedWines().get(2).getName());
                break;
            case "category":
                btnAnswer1.setText(mViewModel.getSelectedWines().get(0).getCategory());
                btnAnswer2.setText(mViewModel.getSelectedWines().get(1).getCategory());
                btnAnswer3.setText(mViewModel.getSelectedWines().get(2).getCategory());
                break;
            case "glass":
                btnAnswer1.setText(Float.toString(mViewModel.getSelectedWines().get(0).getGlassPrice()));
                btnAnswer2.setText(Float.toString(mViewModel.getSelectedWines().get(1).getGlassPrice()));
                btnAnswer3.setText(Float.toString(mViewModel.getSelectedWines().get(2).getGlassPrice()));
                break;
            case "bottle":
                btnAnswer1.setText(Float.toString(mViewModel.getSelectedWines().get(0).getBottlePrice()));
                btnAnswer2.setText(Float.toString(mViewModel.getSelectedWines().get(1).getBottlePrice()));
                btnAnswer3.setText(Float.toString(mViewModel.getSelectedWines().get(2).getBottlePrice()));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mViewModel.getSelectedQuestion().getType());
        }


        final TextView tvTimer = findViewById(R.id.tv_timer);
        tvTimer.setText("10");
        countDownTimer = new CountDownTimer(10000, 10) {

            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished < 5000){
                    tvTimer.setBackground(getResources().getDrawable(R.drawable.background_timer_yellow,null));
                }
                if(millisUntilFinished < 2500){
                    tvTimer.setBackground(getResources().getDrawable(R.drawable.background_timer_orange,null));

                }

                String hms = String.format("%2d:%1d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished),
                        (TimeUnit.MILLISECONDS.toMillis(millisUntilFinished) % TimeUnit.SECONDS.toMillis(1))/10);
                tvTimer.setText(hms);
            }

            public void onFinish() {
                tvTimer.setBackground(getResources().getDrawable(R.drawable.background_timer_red,null));
                tvTimer.setText("0");
                mViewModel.getGameState().set(5,mViewModel.getGameState().get(5)-1);//5 is for lives left
                mViewModel.getGameState().set(3,FALSE);

                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    public void run() {
                        showCorrectAnswer();
                    }

                }, 1000);
                if (mViewModel.getGameState().get(5) > 0) {
                    h.postDelayed(new Runnable() {
                        public void run() {
                            Button btnAnswer1 = findViewById(R.id.btn_play_answer1);
                            Button btnAnswer2 = findViewById(R.id.btn_play_answer2);
                            Button btnAnswer3 = findViewById(R.id.btn_play_answer3);
                            mViewModel.generateQuestion();
                            mViewModel.generateAnswerAndChoices();
                            setGameLayout();
                            setQuestionLayout();
                            setAnswersLayout();
                            resetAnswerButtons(btnAnswer1,btnAnswer2,btnAnswer3);
                            tvTimer.setBackground(getResources().getDrawable(R.drawable.background_timer,null));
                        }

                    }, 2000);
                } else {
                    mViewModel.getGameState().set(5,3);
                    scoreAlertDialog();
                }
            }
        }.start();

    }
    private void setButtonBackgrounds(Button btnAnswer1, Button btnAnswer2, Button btnAnswer3) {
        if (mViewModel.getGameState().get(0) == GREEN) {
            btnAnswer1.setBackgroundResource(R.drawable.background_button_navigation_green);

        } else if (mViewModel.getGameState().get(0) == RED) {
            btnAnswer1.setBackgroundResource(R.drawable.background_button_navigation_red);
            btnAnswer1.setForeground(getResources().getDrawable(R.drawable.ic_wrong_black_24dp, null));
        }

        if (mViewModel.getGameState().get(1) == GREEN) {
            btnAnswer2.setBackgroundResource(R.drawable.background_button_navigation_green);

        } else if (mViewModel.getGameState().get(1) == RED) {
            btnAnswer2.setBackgroundResource(R.drawable.background_button_navigation_red);
            btnAnswer2.setForeground(getResources().getDrawable(R.drawable.ic_wrong_black_24dp, null));
        }

        if (mViewModel.getGameState().get(2) == GREEN) {
            btnAnswer3.setBackgroundResource(R.drawable.background_button_navigation_green);
        } else if (mViewModel.getGameState().get(2) == RED) {
            btnAnswer3.setBackgroundResource(R.drawable.background_button_navigation_red);
            btnAnswer3.setForeground(getResources().getDrawable(R.drawable.ic_wrong_black_24dp, null));
        }

    }
    private void setScoreLayout() {
        TextView tvScore = findViewById(R.id.play_tv_score);
        tvScore.setText(Integer.toString(mViewModel.getScore()));
    }
    private void showCorrectAnswer() {
        switch (mViewModel.getGameState().get(4)) {
            case 0:
                findViewById(R.id.btn_play_answer1).setBackgroundResource(R.drawable.background_button_navigation_green);
                mViewModel.getGameState().set(0, GREEN);
                break;
            case 1:
                findViewById(R.id.btn_play_answer2).setBackgroundResource(R.drawable.background_button_navigation_green);
                mViewModel.getGameState().set(1, GREEN);
                break;
            case 2:
                findViewById(R.id.btn_play_answer3).setBackgroundResource(R.drawable.background_button_navigation_green);
                mViewModel.getGameState().set(2, GREEN);
                break;
        }
    }
    private void resetAnswerButtons(Button answer1,Button answer2,Button answer3) {
        answer1.setBackgroundResource(R.drawable.background_button_navigation);
        answer2.setBackgroundResource(R.drawable.background_button_navigation);
        answer3.setBackgroundResource(R.drawable.background_button_navigation);

        answer1.setForeground(getResources().getDrawable(R.drawable.ic_clear_foreground_24dp, null));
        answer2.setForeground(getResources().getDrawable(R.drawable.ic_clear_foreground_24dp, null));
        answer3.setForeground(getResources().getDrawable(R.drawable.ic_clear_foreground_24dp, null));

        mViewModel.getGameState().set(3,TRUE);

        mViewModel.getGameState().set(0,WHITE);
        mViewModel.getGameState().set(1,WHITE);
        mViewModel.getGameState().set(2,WHITE);
    }

    //Dialogs
    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity2.this);
        builder.setCancelable(false);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_are_you_sure, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvQuestion = alertDialog.findViewById(R.id.tv_are_you_sure_dialog_question);
        Button btnYes = alertDialog.findViewById(R.id.btn_yes);
        Button btnCancel = alertDialog.findViewById(R.id.btn_cancel);

        tvQuestion.setText("Are you sure you want to quit the current game?");

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setIsGameActive(false);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mViewModel.getGameState().set(6,FALSE);
                alertDialog.dismiss();
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                mViewModel.getGameState().set(6,FALSE);

            }
        });

    }
    private void scoreAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity2.this);
        builder.setCancelable(false);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_game_over, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final TextView tvScore = dialogView.findViewById(R.id.tv_score);
        final EditText etName = dialogView.findViewById(R.id.et_name);

        tvScore.setText(Integer.toString(mViewModel.getScore()));

        Button btnRetry = dialogView.findViewById(R.id.btn_retry);
        Button btnHome = dialogView.findViewById(R.id.btn_home);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().equals("")){
                    Toast.makeText(PlayActivity2.this, "You need to write your name", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), PlayActivity2.class);
                    if(mViewModel.saveScoreToDatabase(etName.getText().toString(), Integer.parseInt(tvScore.getText().toString().substring(0, 1)))){
                        Toast.makeText(PlayActivity2.this, "You're all right", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(PlayActivity2.this, "Play some more", Toast.LENGTH_SHORT).show();
                    }                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mViewModel.getGameState().set(7,FALSE);
                    alertDialog.dismiss();
                    startActivity(intent);
                }

            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().equals("")){
                    Toast.makeText(PlayActivity2.this, "You need to write your name", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    if(mViewModel.saveScoreToDatabase(etName.getText().toString(), Integer.parseInt(tvScore.getText().toString().substring(0, 1)))){
                        Toast.makeText(PlayActivity2.this, "You're all right", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(PlayActivity2.this, "Play some more", Toast.LENGTH_SHORT).show();
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mViewModel.getGameState().set(7,FALSE);
                    alertDialog.dismiss();
                    startActivity(intent);
                }
            }
        });
        tvScore.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_tv_score));
    }
}