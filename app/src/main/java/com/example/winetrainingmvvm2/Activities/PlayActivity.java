package com.example.winetrainingmvvm2.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.Models.Score;
import com.example.winetrainingmvvm2.Models.Wine;
import com.example.winetrainingmvvm2.R;
import com.example.winetrainingmvvm2.ViewModels.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    private static final String TAG = "PlayActivity";

    public static final int NAMES = 1;
    public static final int CATEGORIES = 2;
    public static final int PRICES = 3;
    public static final int ALL = 4;
    private List<Wine> mAllWines;
    private List<Question> mAllQuestions = new ArrayList<>();
    private List<Score> mAllScores = new ArrayList<>();
    private ViewModel mViewModel;

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


        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAllWines.isEmpty() && !mAllQuestions.isEmpty()) {
                    mViewModel.setChosenType(NAMES);
                    setGameLayout();
                    pickRandomQuestion();
                    pickRandomWinesAndAnswer();
                    setQuestion();
                    setAnswers();
                } else {
                    Toast.makeText(PlayActivity.this,
                            "You need to have at least three Wines and one question of this type added in your lists",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAllWines.isEmpty() && !mAllQuestions.isEmpty()) {
                    mViewModel.setChosenType(CATEGORIES);
                    setGameLayout();
                    pickRandomQuestion();
                    pickRandomWinesAndAnswer();
                    setQuestion();
                    setAnswers();
                } else {
                    Toast.makeText(PlayActivity.this,
                            "You need to have at least three Wines and one question of this type added in your lists",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAllWines.isEmpty() && !mAllQuestions.isEmpty()) {
                    mViewModel.setChosenType(PRICES);

                    setGameLayout();
                    pickRandomQuestion();
                    pickRandomWinesAndAnswer();
                    setQuestion();
                    setAnswers();
                } else {
                    Toast.makeText(PlayActivity.this,
                            "You need to have at least three Wines and one question of this type added in your lists",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAllWines.isEmpty() && !mAllQuestions.isEmpty()) {
                    mViewModel.setChosenType(ALL);
                    setGameLayout();
                    pickRandomQuestion();
                    pickRandomWinesAndAnswer();
                    setQuestion();
                    setAnswers();
                } else {
                    Toast.makeText(PlayActivity.this,
                            "You need to have at least three Wines and one question of this type added in your lists",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(btnAnswer1);
                setNumberOfQuestionsLeft(mViewModel.getNumberOfQuestions() - 1);

                btnAnswer1.setClickable(false);
                btnAnswer2.setClickable(false);
                btnAnswer3.setClickable(false);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    public void run() {
                        showCorrectAnswer();
                    }

                }, 1000);

                if (mViewModel.getNumberOfQuestions() > 0) {
                    h.postDelayed(new Runnable() {
                        public void run() {
                            resetAnswerButtons();
                            pickRandomQuestion();
                            pickRandomWinesAndAnswer();
                            setQuestion();
                            setAnswers();
                        }

                    }, 2000);

                } else {
                    mViewModel.setNumberOfQuestions(10);
                    scoreAlertDialog();
                }

            }
        });

        btnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(btnAnswer2);
                setNumberOfQuestionsLeft(mViewModel.getNumberOfQuestions() - 1);

                btnAnswer1.setClickable(false);
                btnAnswer2.setClickable(false);
                btnAnswer3.setClickable(false);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    public void run() {
                        showCorrectAnswer();
                    }

                }, 1000);
                if (mViewModel.getNumberOfQuestions() > 0) {
                    h.postDelayed(new Runnable() {
                        public void run() {
                            resetAnswerButtons();
                            pickRandomQuestion();
                            pickRandomWinesAndAnswer();
                            setQuestion();
                            setAnswers();
                        }

                    }, 2000);
                } else {
                    mViewModel.setNumberOfQuestions(10);
                    scoreAlertDialog();
                }


            }
        });

        btnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(btnAnswer3);
                setNumberOfQuestionsLeft(mViewModel.getNumberOfQuestions() - 1);

                btnAnswer1.setClickable(false);
                btnAnswer2.setClickable(false);
                btnAnswer3.setClickable(false);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    public void run() {
                        showCorrectAnswer();
                    }

                }, 1000);
                if (mViewModel.getNumberOfQuestions() > 0) {
                    h.postDelayed(new Runnable() {
                        public void run() {
                            resetAnswerButtons();
                            pickRandomQuestion();
                            pickRandomWinesAndAnswer();
                            setQuestion();
                            setAnswers();
                        }

                    }, 2000);
                } else {
                    mViewModel.setNumberOfQuestions(10);
                    scoreAlertDialog();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        exitDialog();
    }

    private void printScore() {
        Log.d(TAG, "onClick: THE NUMBER OF SCORES IS " + mAllScores.size() + "+++++++++++++++++++++++++++++++++");
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);

        mViewModel.getAllWines().observe(this, new Observer<List<Wine>>() {
            @Override
            public void onChanged(List<Wine> wines) {
                if (wines != null) {
                    mAllWines = wines;
                }
            }
        });

        mViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                if (questions != null) {
                    mAllQuestions = questions;
                }
            }
        });

        mViewModel.getAllScores().observe(this, new Observer<List<Score>>() {
            @Override
            public void onChanged(List<Score> scores) {
                if (scores != null) {
                    mAllScores = scores;
                    printScore();
                }
            }
        });
    }

    private void setGameLayout() {
        ConstraintLayout categoriesLayout = findViewById(R.id.layout_categories);
        ConstraintLayout playLayout = findViewById(R.id.layout_play);

        categoriesLayout.setVisibility(View.GONE);
        playLayout.setVisibility(View.VISIBLE);

    }

    private void pickRandomWinesAndAnswer() {
        //Add three random wines with different categories to an empty CurrentQuestionWines List
        mViewModel.getSelectedWines().clear();
        Random r = new Random();

        //Add first wine
        mViewModel.getSelectedWines().add(mAllWines.get(r.nextInt(mAllWines.size())));

        //Add second and third wines
        int winesAdded = 1;
        while (winesAdded < 3) {
            int randomWineIndex = r.nextInt(mAllWines.size());
            Wine possibleWine = mAllWines.get(randomWineIndex);

            if (!doesSelectedWineListContain(mViewModel.getSelectedQuestion().get(0).getType(), possibleWine)) {
                mViewModel.getSelectedWines().add(mAllWines.get(randomWineIndex));
                winesAdded++;
            }

        }

        mViewModel.setAnswerWine();
    }

    private boolean doesSelectedWineListContain(String type, Wine possibleWine) {

        if (mViewModel.getSelectedWines().contains(possibleWine)) {
            return true;
        }

        for (Wine selectedWine : mViewModel.getSelectedWines()) {

            //Checking if whatever the answer is is already in the array
            switch (type) {
                case "name":
                    if (possibleWine.getName().equals(selectedWine.getName())) {
                        return true;
                    }
                    break;
                case "category":
                    if (possibleWine.getCategory().equals(selectedWine.getCategory())) {
                        return true;
                    }
                    break;
                case "glass":
                    if (possibleWine.getGlassPrice() == selectedWine.getGlassPrice()) {
                        return true;
                    }
                    break;
                case "bottle":
                    if (possibleWine.getBottlePrice() == selectedWine.getBottlePrice()) {
                        return true;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + mViewModel.getSelectedQuestion().get(0).getType());
            }
        }

        return false;
    }

    private void pickRandomQuestion() {
        int i = 0;
        do {
            mViewModel.getSelectedQuestion().clear();
            Random r = new Random();
            Question possibleQuestion = mAllQuestions.get(r.nextInt(mAllQuestions.size()));

            if (mViewModel.getChosenType() == NAMES && possibleQuestion.getType().equals("name")) {
                mViewModel.getSelectedQuestion().add(possibleQuestion);
                i++;
            } else if (mViewModel.getChosenType() == CATEGORIES && possibleQuestion.getType().equals("category")) {
                mViewModel.getSelectedQuestion().add(possibleQuestion);
                i++;
            } else if (mViewModel.getChosenType() == PRICES && (possibleQuestion.getType().equals("glass") || possibleQuestion.getType().equals("bottle"))) {
                mViewModel.getSelectedQuestion().add(possibleQuestion);
                i++;
            } else if (mViewModel.getChosenType() == ALL) {
                mViewModel.getSelectedQuestion().add(possibleQuestion);
                i++;
            }

        } while (i < 1);


    }

    private void setQuestion() {
        if (!mViewModel.getSelectedWines().isEmpty()) {
            Random r = new Random();
            int randomIndex = r.nextInt(mViewModel.getSelectedQuestion().size());

            String currentQuestion = mViewModel.getSelectedQuestion().get(randomIndex).getQuestion();

            String one, two, three;
            one = currentQuestion.substring(0, mViewModel.getSelectedQuestion().get(randomIndex).getQuestion().indexOf("#"));
            two = currentQuestion.substring(currentQuestion.indexOf("!") + 1);


            if (currentQuestion.contains("#name")) {
                three = one + mViewModel.getAnswerWine().getName() + two;
            } else if (currentQuestion.contains("#category!")) {
                three = one + mViewModel.getAnswerWine().getCategory() + two;
            } else if (currentQuestion.contains("#bottle!")) {
                three = one + mViewModel.getAnswerWine().getBottlePrice() + two;
            } else if (currentQuestion.contains("#glass!")) {
                three = one + mViewModel.getAnswerWine().getGlassPrice() + two;
            } else {
                three = one + two;
            }

            TextView tvQuestion = findViewById(R.id.tv_play_question);
            tvQuestion.setText(three);

        } else {
            Log.d(TAG, "setQuestion: ERROR currentWinesList is empty");
        }
    }

    private void setAnswers() {
        Button btnAnswer1 = findViewById(R.id.btn_play_answer1);
        Button btnAnswer2 = findViewById(R.id.btn_play_answer2);
        Button btnAnswer3 = findViewById(R.id.btn_play_answer3);


        switch (mViewModel.getSelectedQuestion().get(0).getType()) {
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
                throw new IllegalStateException("Unexpected value: " + mViewModel.getSelectedQuestion().get(0).getType());
        }
    }

    private void checkAnswer(Button button) {

        switch (mViewModel.getSelectedQuestion().get(0).getType()) {
            case "name":
                if (mViewModel.getAnswerWine().getName().equals(button.getText().toString())) {
                    button.setBackgroundResource(R.drawable.background_button_navigation_green);
                    mViewModel.setScore(mViewModel.getScore() + 1);
                } else {
                    button.setBackgroundResource(R.drawable.background_button_navigation_red);
                    button.setForeground(getResources().getDrawable(R.drawable.ic_wrong_black_24dp, null));
                }
                break;
            case "category":
                if (mViewModel.getAnswerWine().getCategory().equals(button.getText().toString())) {
                    button.setBackgroundResource(R.drawable.background_button_navigation_green);
                    mViewModel.setScore(mViewModel.getScore() + 1);
                } else {
                    button.setBackgroundResource(R.drawable.background_button_navigation_red);
                    button.setForeground(getResources().getDrawable(R.drawable.ic_wrong_black_24dp, null));
                }
                break;
            case "glass":
                if (mViewModel.getAnswerWine().getGlassPrice() == Float.parseFloat(button.getText().toString())) {
                    button.setBackgroundResource(R.drawable.background_button_navigation_green);
                    mViewModel.setScore(mViewModel.getScore() + 1);
                } else {
                    button.setBackgroundResource(R.drawable.background_button_navigation_red);
                    button.setForeground(getResources().getDrawable(R.drawable.ic_wrong_black_24dp, null));
                }
                break;
            case "bottle":
                if (mViewModel.getAnswerWine().getBottlePrice() == Float.parseFloat(button.getText().toString())) {
                    button.setBackgroundResource(R.drawable.background_button_navigation_green);
                    mViewModel.setScore(mViewModel.getScore() + 1);
                } else {
                    button.setBackgroundResource(R.drawable.background_button_navigation_red);
                    button.setForeground(getResources().getDrawable(R.drawable.ic_wrong_black_24dp, null));
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mViewModel.getSelectedQuestion().get(0).getType());
        }
    }

    private void resetAnswerButtons() {
        Button answer1 = findViewById(R.id.btn_play_answer1);
        Button answer2 = findViewById(R.id.btn_play_answer2);
        Button answer3 = findViewById(R.id.btn_play_answer3);
        answer1.setBackgroundResource(R.drawable.background_button_navigation);
        answer1.setForeground(getResources().getDrawable(R.drawable.ic_clear_foreground_24dp, null));
        answer1.setClickable(true);
        answer2.setBackgroundResource(R.drawable.background_button_navigation);
        answer2.setForeground(getResources().getDrawable(R.drawable.ic_clear_foreground_24dp, null));
        answer2.setClickable(true);
        answer3.setBackgroundResource(R.drawable.background_button_navigation);
        answer3.setForeground(getResources().getDrawable(R.drawable.ic_clear_foreground_24dp, null));
        answer3.setClickable(true);

    }

    private void showCorrectAnswer() {
        Button answer1 = findViewById(R.id.btn_play_answer1);
        Button answer2 = findViewById(R.id.btn_play_answer2);
        Button answer3 = findViewById(R.id.btn_play_answer3);


        if (answer1.getText().equals(mViewModel.getAnswerWine().getCategory())) {
            answer1.setBackgroundResource(R.drawable.background_button_navigation_green);
        } else if (answer2.getText().equals(mViewModel.getAnswerWine().getCategory())) {
            answer2.setBackgroundResource(R.drawable.background_button_navigation_green);

        } else if (answer3.getText().equals(mViewModel.getAnswerWine().getCategory())) {
            answer3.setBackgroundResource(R.drawable.background_button_navigation_green);
        }

        switch (mViewModel.getSelectedQuestion().get(0).getType()) {
            case "name":
                if (answer1.getText().equals(mViewModel.getAnswerWine().getName())) {
                    answer1.setBackgroundResource(R.drawable.background_button_navigation_green);
                } else if (answer2.getText().equals(mViewModel.getAnswerWine().getName())) {
                    answer2.setBackgroundResource(R.drawable.background_button_navigation_green);

                } else if (answer3.getText().equals(mViewModel.getAnswerWine().getName())) {
                    answer3.setBackgroundResource(R.drawable.background_button_navigation_green);
                }
                break;
            case "category":
                if (answer1.getText().equals(mViewModel.getAnswerWine().getCategory())) {
                    answer1.setBackgroundResource(R.drawable.background_button_navigation_green);
                } else if (answer2.getText().equals(mViewModel.getAnswerWine().getCategory())) {
                    answer2.setBackgroundResource(R.drawable.background_button_navigation_green);

                } else if (answer3.getText().equals(mViewModel.getAnswerWine().getCategory())) {
                    answer3.setBackgroundResource(R.drawable.background_button_navigation_green);
                }
                break;
            case "glass":
                if (Float.parseFloat(answer1.getText().toString()) == mViewModel.getAnswerWine().getGlassPrice()) {
                    answer1.setBackgroundResource(R.drawable.background_button_navigation_green);
                } else if (Float.parseFloat(answer2.getText().toString()) == mViewModel.getAnswerWine().getGlassPrice()) {
                    answer2.setBackgroundResource(R.drawable.background_button_navigation_green);
                } else if (Float.parseFloat(answer3.getText().toString()) == mViewModel.getAnswerWine().getGlassPrice()) {
                    answer3.setBackgroundResource(R.drawable.background_button_navigation_green);
                }
                break;
            case "bottle":
                if (Float.parseFloat(answer1.getText().toString()) == mViewModel.getAnswerWine().getBottlePrice()) {
                    answer1.setBackgroundResource(R.drawable.background_button_navigation_green);
                } else if (Float.parseFloat(answer2.getText().toString()) == mViewModel.getAnswerWine().getBottlePrice()) {
                    answer2.setBackgroundResource(R.drawable.background_button_navigation_green);
                } else if (Float.parseFloat(answer3.getText().toString()) == mViewModel.getAnswerWine().getBottlePrice()) {
                    answer3.setBackgroundResource(R.drawable.background_button_navigation_green);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mViewModel.getSelectedQuestion().get(0).getType());
        }
    }

    private void setNumberOfQuestionsLeft(int numberOfQuestions) {
        mViewModel.setNumberOfQuestions(numberOfQuestions);
    }

    private void scoreAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
        builder.setCancelable(false);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_game_over, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final TextView tvScore = dialogView.findViewById(R.id.tv_score);
        final EditText etName = dialogView.findViewById(R.id.et_name);
        tvScore.setText(mViewModel.getScore() + "/" + mViewModel.getNumberOfQuestions());

        Button btnRetry = dialogView.findViewById(R.id.btn_retry);
        Button btnHome = dialogView.findViewById(R.id.btn_home);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                saveScoreToDatabase(etName.getText().toString(), Integer.parseInt(tvScore.getText().toString().substring(0, 1)));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                saveScoreToDatabase(etName.getText().toString(), Integer.parseInt(tvScore.getText().toString().substring(0, 1)));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void saveScoreToDatabase(String name, int score) {
        Score possibleHighScore = new Score(score, name, 1, mViewModel.getChosenType());
        int place = mAllScores.size() + 1;

        if (mAllScores.isEmpty()) {
            possibleHighScore.setPlace(1);
            mViewModel.insert(possibleHighScore);
        } else {
            //Find out if score is good enough to be saved
            for (Score savedScore : mAllScores) {
                //check to see what type the score is and filter on that then
                if (savedScore.getType() == mViewModel.getChosenType()) {
                    //check to see if this score is higher than any of the others
                    if (possibleHighScore.getScore() > savedScore.getScore()) {
                        place--;
                    }
                }
            }


            if (place < 4) {
                //If it is good enough to be saved then grab that current place id and set it to
                //the new score so it gets replaced in the dao insert method
                for (Score savedScore : mAllScores) {
                    if (savedScore.getPlace() == place) {
                        possibleHighScore.setId(savedScore.getId());
                    }
                }


                possibleHighScore.setPlace(place);

                mViewModel.insert(possibleHighScore);
            } else {
                Log.d(TAG, "saveScoreToDatabase: Score is not good enough to be saved, score: " + place);
            }
        }
    }

    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
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
                alertDialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private void clearScores() {
        mViewModel.deleteAllScores();
        Toast.makeText(this, "Scores Cleared", Toast.LENGTH_SHORT).show();
    }
}