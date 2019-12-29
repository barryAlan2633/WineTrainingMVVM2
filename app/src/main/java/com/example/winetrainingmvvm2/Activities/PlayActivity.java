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
import android.widget.Switch;
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
    ViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start of onCreate");
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
                Log.d(TAG, "onClick: THE NUMBER OF SCORES IS " + mAllScores.size() + "===============================");
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
                    Log.d(TAG, "onClick: THE NUMBER OF SCORES IS " + mAllScores.size() + "---------------------");

                } else {
                    mViewModel.setNumberOfQuestions(10);

                    Log.d(TAG, "onClick: THE NUMBER OF SCORES IS " + mAllScores.size() + "---------------------");
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
        Intent intent = new Intent (getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    private void printScore() {
        Log.d(TAG, "onClick: THE NUMBER OF SCORES IS " + mAllScores.size() + "+++++++++++++++++++++++++++++++++");

    }

    private void initViewModel() {
        Log.d(TAG, "initViewModel: start init viewModel");
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);

        mViewModel.getAllWines().observe(this, new Observer<List<Wine>>() {
            @Override
            public void onChanged(List<Wine> wines) {
                if (wines != null) {
                    mAllWines = wines;
                    Log.d(TAG, "onChanged: updating winelist");
                }
            }
        });

        mViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                if (questions != null) {
                    mAllQuestions = questions;
                    Log.d(TAG, "onChanged: updating questionlist");

                }
            }
        });

        mViewModel.getAllScores().observe(this, new Observer<List<Score>>() {
            @Override
            public void onChanged(List<Score> scores) {
                if (scores != null) {
                    mAllScores = scores;
                    Log.d(TAG, "onChanged: updating scorelist");
                    printScore();
                }
            }
        });
        Log.d(TAG, "initViewModel: finish init viewModel");


    }

    private void setGameLayout() {
        Log.d(TAG, "setGameLayout: started setting game layout with " + mViewModel.getChosenType() + " as the type");
        ConstraintLayout categoriesLayout = findViewById(R.id.layout_categories);
        ConstraintLayout playLayout = findViewById(R.id.layout_play);

        categoriesLayout.setVisibility(View.GONE);
        playLayout.setVisibility(View.VISIBLE);
        Log.d(TAG, "setGameLayout: finished setting game layout with " + mViewModel.getChosenType() + " as the type");

    }

    private void pickRandomWinesAndAnswer() {
        //Add three random wines with different categories to an empty CurrentQuestionWines List
        mViewModel.getCurrentWines().clear();
        Random r = new Random();

        //Add first wine
        mViewModel.getCurrentWines().add(mAllWines.get(r.nextInt(mAllWines.size())));
        Log.d(TAG, "pickRandomWinesAndAnswer: added " + mViewModel.getCurrentWines().get(0).getName() + " category: " + mViewModel.getCurrentWines().get(0).getCategory());

        //Add second and third wines
        int count = 0;
        int i = 0;
        do {
            int randomWineIndex = r.nextInt(mAllWines.size());

            if (!mViewModel.getCurrentWines().isEmpty()) {
                Wine possibleWine = mAllWines.get(randomWineIndex);

                for (Wine wine : mViewModel.getCurrentWines()) {
                    if (mViewModel.getCurrentWines().contains(possibleWine)) {
                        i = 0;
                        Log.d(TAG, "pickRandomWinesAndAnswer: skipped " + possibleWine.getName() + " category: " + possibleWine.getCategory());
                        break;
                    }

                    //Checking if whatever the answer is is already in the array
                    switch (mViewModel.getCurrentQuestion().get(0).getType()) {
                        case "name":
                            if (!possibleWine.getName().equals(wine.getName())) {
                                count++;
                            } else {
                                count = 0;
                            }
                            break;
                        case "category":
                            if (!possibleWine.getCategory().equals(wine.getCategory())) {
                                count++;
                            } else {
                                count = 0;
                            }
                            break;
                        case "glass":
                            if (possibleWine.getGlassPrice() != wine.getGlassPrice()) {
                                count++;
                            } else {
                                count = 0;
                            }
                            break;
                        case "bottle":
                            if (possibleWine.getBottlePrice() != wine.getBottlePrice()) {
                                count++;
                            } else {
                                count = 0;
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + mViewModel.getCurrentQuestion().get(0).getType());
                    }

                    if (count == mViewModel.getCurrentWines().size()) {
                        break;
                    }
                }
                if (count == mViewModel.getCurrentWines().size()) {
                    mViewModel.getCurrentWines().add(mAllWines.get(randomWineIndex));
                    Log.d(TAG, "pickRandomWinesAndAnswer: added " + possibleWine.getName() + " category: " + possibleWine.getCategory());
                    i++;
                    count = 0;
                }
            }
        }
        while (i < 2);

        mViewModel.setAnswerWine();

        Log.d(TAG, "pickRandomWinesAndAnswer: finished picking answers");
    }

    private void pickRandomQuestion() {
        Log.d(TAG, "pickRandomQuestion: starting to choose random question");
        int i = 0;
        do {
            mViewModel.getCurrentQuestion().clear();
            Random r = new Random();
            Question possibleQuestion = mAllQuestions.get(r.nextInt(mAllQuestions.size()));

            if (mViewModel.getChosenType() == NAMES && possibleQuestion.getType().equals("name")) {
                mViewModel.getCurrentQuestion().add(possibleQuestion);
                Log.d(TAG, "pickRandomQuestion: Saving " + possibleQuestion.getQuestion() + " from type name to viewModel");
                i++;
            } else if (mViewModel.getChosenType() == CATEGORIES && possibleQuestion.getType().equals("category")) {
                mViewModel.getCurrentQuestion().add(possibleQuestion);
                Log.d(TAG, "pickRandomQuestion: Saving " + possibleQuestion.getQuestion() + " from type category to viewModel");
                i++;
            } else if (mViewModel.getChosenType() == PRICES && (possibleQuestion.getType().equals("glass") || possibleQuestion.getType().equals("bottle"))) {
                mViewModel.getCurrentQuestion().add(possibleQuestion);
                Log.d(TAG, "pickRandomQuestion: Saving " + possibleQuestion.getQuestion() + " from type price to viewModel");
                i++;
            } else if (mViewModel.getChosenType() == ALL) {
                mViewModel.getCurrentQuestion().add(possibleQuestion);
                Log.d(TAG, "pickRandomQuestion: Saving " + possibleQuestion.getQuestion() + " from type all to viewModel");
                i++;
            }

        } while (i < 1);


        Log.d(TAG, "pickRandomQuestion: finished choosing random question");
    }

    private void setQuestion() {
        Log.d(TAG, "setQuestion: Starting to set question on UI");
        if (!mViewModel.getCurrentWines().isEmpty()) {
            Random r = new Random();
            int randomIndex = r.nextInt(mViewModel.getCurrentQuestion().size());

            Log.d(TAG, "setQuestion: Trying to retrieve a question from viewModel ");
            String currentQuestion = mViewModel.getCurrentQuestion().get(randomIndex).getQuestion();
            Log.d(TAG, "setQuestion: Retrieved question from viewModel ");

            Log.d(TAG, "setQuestion: Trying to replace placeholders if they exist");
            String one, two, three;
            one = currentQuestion.substring(0, mViewModel.getCurrentQuestion().get(randomIndex).getQuestion().indexOf("#"));
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
            Log.d(TAG, "setQuestion: Done replacing placeholders if they exist");

            Log.d(TAG, "setQuestion: Trying to set question to UI");
            TextView tvQuestion = findViewById(R.id.tv_play_question);
            tvQuestion.setText(three);
            Log.d(TAG, "setQuestion: Finished setting question to UI");

        } else {
            Log.d(TAG, "setQuestion: ERROR currentWinesList is empty");
        }
        Log.d(TAG, "setQuestion: Finished setting question on UI");

    }

    private void setAnswers() {
        Log.d(TAG, "setAnswers: Trying to set answers to UI");
        Button btnAnswer1 = findViewById(R.id.btn_play_answer1);
        Button btnAnswer2 = findViewById(R.id.btn_play_answer2);
        Button btnAnswer3 = findViewById(R.id.btn_play_answer3);


        switch (mViewModel.getCurrentQuestion().get(0).getType()) {
            case "name":
                btnAnswer1.setText(mViewModel.getCurrentWines().get(0).getName());
                btnAnswer2.setText(mViewModel.getCurrentWines().get(1).getName());
                btnAnswer3.setText(mViewModel.getCurrentWines().get(2).getName());
                break;
            case "category":
                btnAnswer1.setText(mViewModel.getCurrentWines().get(0).getCategory());
                btnAnswer2.setText(mViewModel.getCurrentWines().get(1).getCategory());
                btnAnswer3.setText(mViewModel.getCurrentWines().get(2).getCategory());
                break;
            case "glass":
                btnAnswer1.setText(Float.toString(mViewModel.getCurrentWines().get(0).getGlassPrice()));
                btnAnswer2.setText(Float.toString(mViewModel.getCurrentWines().get(1).getGlassPrice()));
                btnAnswer3.setText(Float.toString(mViewModel.getCurrentWines().get(2).getGlassPrice()));
                break;
            case "bottle":
                btnAnswer1.setText(Float.toString(mViewModel.getCurrentWines().get(0).getBottlePrice()));
                btnAnswer2.setText(Float.toString(mViewModel.getCurrentWines().get(1).getBottlePrice()));
                btnAnswer3.setText(Float.toString(mViewModel.getCurrentWines().get(2).getBottlePrice()));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mViewModel.getCurrentQuestion().get(0).getType());
        }
        Log.d(TAG, "setAnswers: Finished setting answers to UI");
    }

    private void checkAnswer(Button button) {
        Log.d(TAG, "checkAnswer: Trying to check answer");

        switch (mViewModel.getCurrentQuestion().get(0).getType()) {
            case "name":
                if (mViewModel.getAnswerWine().getName().equals(button.getText().toString())) {
                    Log.d(TAG, "checkAnswer: answer is correct");
                    button.setBackgroundResource(R.drawable.background_button_navigation_green);
                    mViewModel.setScore(mViewModel.getScore() + 1);
                    Log.d(TAG, "checkAnswer: score: " + mViewModel.getScore() + " was saved into viewModel");
                } else {
                    Log.d(TAG, "checkAnswer: answer is incorrect");
                    button.setBackgroundResource(R.drawable.background_button_navigation_red);
                    button.setForeground(getResources().getDrawable(R.drawable.ic_wrong_black_24dp, null));
                }
                break;
            case "category":
                if (mViewModel.getAnswerWine().getCategory().equals(button.getText().toString())) {
                    Log.d(TAG, "checkAnswer: answer is correct");
                    button.setBackgroundResource(R.drawable.background_button_navigation_green);
                    mViewModel.setScore(mViewModel.getScore() + 1);
                    Log.d(TAG, "checkAnswer: score: " + mViewModel.getScore() + " was saved into viewModel");
                } else {
                    Log.d(TAG, "checkAnswer: answer is incorrect");
                    button.setBackgroundResource(R.drawable.background_button_navigation_red);
                    button.setForeground(getResources().getDrawable(R.drawable.ic_wrong_black_24dp, null));
                }
                break;
            case "glass":
                if (mViewModel.getAnswerWine().getGlassPrice() == Float.parseFloat(button.getText().toString())) {
                    Log.d(TAG, "checkAnswer: answer is correct");
                    button.setBackgroundResource(R.drawable.background_button_navigation_green);
                    mViewModel.setScore(mViewModel.getScore() + 1);
                    Log.d(TAG, "checkAnswer: score: " + mViewModel.getScore() + " was saved into viewModel");
                } else {
                    Log.d(TAG, "checkAnswer: answer is incorrect");
                    button.setBackgroundResource(R.drawable.background_button_navigation_red);
                    button.setForeground(getResources().getDrawable(R.drawable.ic_wrong_black_24dp, null));
                }
                break;
            case "bottle":
                if (mViewModel.getAnswerWine().getBottlePrice() == Float.parseFloat(button.getText().toString())) {
                    Log.d(TAG, "checkAnswer: answer is correct");
                    button.setBackgroundResource(R.drawable.background_button_navigation_green);
                    mViewModel.setScore(mViewModel.getScore() + 1);
                    Log.d(TAG, "checkAnswer: score: " + mViewModel.getScore() + " was saved into viewModel");
                } else {
                    Log.d(TAG, "checkAnswer: answer is incorrect");
                    button.setBackgroundResource(R.drawable.background_button_navigation_red);
                    button.setForeground(getResources().getDrawable(R.drawable.ic_wrong_black_24dp, null));
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mViewModel.getCurrentQuestion().get(0).getType());
        }

        Log.d(TAG, "checkAnswer: Finished checking answer");

    }

    private void resetAnswerButtons() {
        Log.d(TAG, "resetAnswerButtons: Trying to reset answer buttons");
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
        Log.d(TAG, "resetAnswerButtons: Finished resetting answer buttons");

    }

    private void showCorrectAnswer() {
        Log.d(TAG, "showCorrectAnswer: Trying to show correct answer");
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

        switch (mViewModel.getCurrentQuestion().get(0).getType()) {
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
                throw new IllegalStateException("Unexpected value: " + mViewModel.getCurrentQuestion().get(0).getType());
        }


        Log.d(TAG, "showCorrectAnswer: Finished showing answer");

    }

    private void setNumberOfQuestionsLeft(int numberOfQuestions) {
        Log.d(TAG, "setNumberOfQuestionsLeft: Trying to set numberOfQuestionsLeft on viewModel");
        mViewModel.setNumberOfQuestions(numberOfQuestions);
        Log.d(TAG, "setNumberOfQuestionsLeft: Number of questions left is " + mViewModel.getNumberOfQuestions());
        Log.d(TAG, "setNumberOfQuestionsLeft: Finished setting numberOfQuestionsLeft on viewModel");

    }

    private void scoreAlertDialog() {
        Log.d(TAG, "scoreAlertDialog: Trying to set score dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
        builder.setCancelable(false);
        View dialogView = getLayoutInflater().inflate(R.layout.play_ended, null);
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
                Log.d(TAG, "onClick: Deleting all scores");
                clearScores();
                Log.d(TAG, "scoreAlertDialog: Finished setting score dialog");
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                saveScoreToDatabase(etName.getText().toString(), Integer.parseInt(tvScore.getText().toString().substring(0, 1)));
                Log.d(TAG, "scoreAlertDialog: Finished setting score dialog");
                startActivity(intent);
            }
        });
    }

    private void saveScoreToDatabase(String name, int score) {
        Log.d(TAG, "saveScoreToDatabase: Starting to save score to database");
        Score possibleHighScore = new Score(score, name, 1, mViewModel.getChosenType());
        int place = mAllScores.size() + 1;
        Log.d(TAG, "saveScoreToDatabase: initial score array size is: " + mAllScores.size());

        if (mAllScores.isEmpty()) {
            Log.d(TAG, "saveScoreToDatabase: the size of the initial score array is zero");
            possibleHighScore.setPlace(1);
            mViewModel.insert(possibleHighScore);
            Log.d(TAG, "saveScoreToDatabase: inserted score" + possibleHighScore.getPlace() + possibleHighScore.getName() + possibleHighScore.getScore() + possibleHighScore.getId());

        } else {
            Log.d(TAG, "saveScoreToDatabase: the size of the initial score array is NOTTT zero");

            //Find out if score is good enough to be saved
            for (Score savedScore : mAllScores) {
                Log.d(TAG, "saveScoreToDatabase: iterating through the scores list");
                //check to see what type the score is and filter on that then
                if (savedScore.getType() == mViewModel.getChosenType()) {
                    Log.d(TAG, "saveScoreToDatabase: This score is of the chosen type");
                    //check to see if this score is higher than any of the others
                    if (possibleHighScore.getScore() > savedScore.getScore()) {
                        place--;
                        Log.d(TAG, "saveScoreToDatabase: This score is lower than what you scored, place is now: " + place);

                    }
                }
            }


            if (place < 4) {
                Log.d(TAG, "saveScoreToDatabase: Score is good enough to be saved, score: " + place);
                //If it is good enough to be saved then grab that current place id and set it to
                //the new score so it gets replaced in the dao insert method
                for (Score savedScore : mAllScores) {
                    if (savedScore.getPlace() == place) {
                        Log.d(TAG, "saveScoreToDatabase: Setting correct id on score item, ID " + savedScore.getId());
                        possibleHighScore.setId(savedScore.getId());
                    }
                }


                possibleHighScore.setPlace(place);

                mViewModel.insert(possibleHighScore);
                Log.d(TAG, "saveScoreToDatabase: Saved score into database");
            } else {
                Log.d(TAG, "saveScoreToDatabase: Score is not good enough to be saved, score: " + place);
            }
        }
        Log.d(TAG, "saveScoreToDatabase: Finished setting score to database");


    }

    private void clearScores() {
        mViewModel.deleteAllScores();
        Toast.makeText(this, "Scores Cleared", Toast.LENGTH_SHORT).show();
    }
}