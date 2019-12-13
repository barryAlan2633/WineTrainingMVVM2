package com.example.winetrainingmvvm2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.Models.Wine;
import com.example.winetrainingmvvm2.R;
import com.example.winetrainingmvvm2.ViewModels.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.view.View.GONE;

public class PlayActivity extends AppCompatActivity {
    private static final String TAG = "PlayActivity";

    private static final int NAMES = 1;
    private static final int CATEGORIES = 2;
    private static final int PRICES = 3;
    private static final int ALL = 4;
    private static int chosenType;
    private List<Wine> mAllWines;
    private List<Wine> mTypeWines = new ArrayList<>();
    private List<Wine> mUsedWines = new ArrayList<>();
    private List<Question> mAllQuestions = new ArrayList<>();
    private List<Question> mTypeQuestions = new ArrayList<>();
    private List<Question> mUsedQuestions;
    ViewModel mViewModel;

    private ConstraintLayout categoriesLayout;
    private ConstraintLayout playLayout;
    TextView tvQuestion;
    Button btnAnswer1;
    Button btnAnswer2;
    Button btnAnswer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        categoriesLayout = findViewById(R.id.layout_categories);
        playLayout = findViewById(R.id.layout_play);
        tvQuestion = findViewById(R.id.tv_play_question);

        Button btnNames = findViewById(R.id.btn_names);
        Button btnCategories = findViewById(R.id.btn_categories);
        Button btnPrices = findViewById(R.id.btn_prices);
        Button btnAll = findViewById(R.id.btn_all);
        btnAnswer1 = findViewById(R.id.btn_answer1);
        btnAnswer2 = findViewById(R.id.btn_answer2);
        btnAnswer3 = findViewById(R.id.btn_answer3);

        btnNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChosenType(NAMES);
                setAnswers();
                setQuestion();
            }
        });
        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChosenType(CATEGORIES);
            }
        });
        btnPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChosenType(PRICES);
            }
        });
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChosenType(ALL);
                //TODO rerun app when you come back press on one of the types
            }
        });

        initViewModel();
        filterQuestions(chosenType);
    }



    public void swapLayouts(int i) {
        if (i == 1) {
            categoriesLayout.setVisibility(View.GONE);
            playLayout.setVisibility(View.VISIBLE);
        } else if (i == 2) {

            categoriesLayout.setVisibility(View.VISIBLE);
            playLayout.setVisibility(GONE);
        } else {
            Toast.makeText(this, "Error swap layouts int is not 1 or 2", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);

        mViewModel.getAllWines().observe(this, new Observer<List<Wine>>() {
            @Override
            public void onChanged(List<Wine> wines) {
                if(wines != null){
                    mAllWines = wines;
                }
            }
        });

        mViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                if(questions != null){
                    mAllQuestions = questions;
                    filterQuestions(ALL);
                }
            }
        });

    }

    public void setChosenType(int type) {
        chosenType = type;
    }

    private void filterQuestions(int type){
        switch(type){
            case NAMES:{
                mTypeQuestions.clear();
                for(Question question : mAllQuestions){
                    if(question.getType().toLowerCase().equals("name")){
                        mTypeQuestions.add(question);
                    }
                }
                break;
            }
            case CATEGORIES:{
                mTypeQuestions.clear();
                for(Question question : mAllQuestions){
                    if(question.getType().toLowerCase().equals("categories")){
                        mTypeQuestions.add(question);
                    }
                }
                break;
            }
            case PRICES:{
                mTypeQuestions.clear();
                for(Question question : mAllQuestions){
                    if(question.getType().toLowerCase().equals("prices")){
                        mTypeQuestions.add(question);
                    }
                }
                break;
            }
            default:{
                mTypeQuestions.clear();
                mTypeQuestions.addAll(mAllQuestions);
                break;
            }
        }
    }
    
    private void setQuestion() {
        if(!mTypeQuestions.isEmpty()){
            int randomQuestion = new Random().nextInt(mTypeQuestions.size());
            Question currentQuestion = mTypeQuestions.get(randomQuestion);
            Wine currentWine = mTypeWines.get(new Random().nextInt(2));


           if(currentQuestion.getQuestion().contains("#name")){
               String one = currentQuestion.getQuestion().substring(0,currentQuestion.getQuestion().indexOf("#"));
               String two = currentQuestion.getQuestion().substring(currentQuestion.getQuestion().indexOf("!") + 1);
               String three = one + currentWine.getName() + two;


               tvQuestion.setText(three);

           }

            swapLayouts(1);
        }
        else{
            Toast.makeText(this, "You must add at least one question in the Edit Question page ",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void setAnswers() {
        int i = 0;
        while(i<3){
            for(Wine wine : mAllWines){
                if(i == 0){
                    mTypeWines.add(wine);
                    i++;
                }
                else if(i == 1){
                    if(!mTypeWines.get(0).getCategory().equals(wine.getCategory())){
                        mTypeWines.add(wine);
                        i++;
                    }
                }
                else if(i == 2){
                    if(!mTypeWines.get(0).getCategory().equals(wine.getCategory()) &&
                            !mTypeWines.get(1).getCategory().equals(wine.getCategory())){
                        mTypeWines.add(wine);
                        i++;
                    }
                }
            }
        }

        btnAnswer1.setText(mTypeWines.get(0).getCategory());
        btnAnswer2.setText(mTypeWines.get(1).getCategory());
        btnAnswer3.setText(mTypeWines.get(2).getCategory());


    }

}
