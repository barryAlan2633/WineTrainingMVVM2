package com.example.winetrainingmvvm2.ViewModels;


import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.Models.Score;
import com.example.winetrainingmvvm2.Models.Wine;
import com.example.winetrainingmvvm2.Repositories.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.example.winetrainingmvvm2.Constants.constants.ALL;
import static com.example.winetrainingmvvm2.Constants.constants.CATEGORIES;
import static com.example.winetrainingmvvm2.Constants.constants.FALSE;
import static com.example.winetrainingmvvm2.Constants.constants.NAMES;
import static com.example.winetrainingmvvm2.Constants.constants.PRICES;
import static com.example.winetrainingmvvm2.Constants.constants.TRUE;
import static com.example.winetrainingmvvm2.Constants.constants.WHITE;
import static java.lang.Float.parseFloat;


public class ViewModel2 extends AndroidViewModel {
    private static final String TAG = "ViewModel2";
    private Repository mRepository;

    private static ArrayList<Question> mTODOAllQuestions = new ArrayList<>();
    private static ArrayList<Wine> mTODOAllWines = new ArrayList<>();
    private static ArrayList<Score> mTODOAllScores = new ArrayList<>();
    private ArrayList<Integer> mGameState = new ArrayList<>(8);
    private static int mScore = 0;
    private static boolean mWineListItemClickable = true;
    private static boolean mQuestionListItemClickable = true;
    private Boolean mIsGameActive = false;
    private static int mChosenType = -1;

    private LiveData<List<Wine>> mAllWines;
    private LiveData<List<Question>> mAllQuestions;
    private LiveData<List<Score>> mAllScores;

    private Question mSelectedQuestion;
    private List<Wine> mSelectedWines = new ArrayList<>();
    private Wine mAnswerWine = null;

    //Constructor
    public ViewModel2(@NonNull Application application) {
        super(application);
        this.mRepository = new Repository(application);
        this.mAllWines = mRepository.getAllWines();
        this.mAllQuestions = mRepository.getAllQuestions();
        this.mAllScores = mRepository.getAllScores();
        mScore = 0;
        mGameState.add(0,WHITE);//0,1,2 for background colors of answer buttons on rotation
        mGameState.add(1,WHITE);
        mGameState.add(2,WHITE);
        mGameState.add(3,TRUE);//3 for clickable of answer buttons on rotation
        mGameState.add(4,-1);//4 index of correct answer in the selected wines array list
        mGameState.add(5,3);//5 lives left
        mGameState.add(6,FALSE);//6 leaving game dialog showing or not
        mGameState.add(7,FALSE);//7 score dialog showing or not
//        deleteAllQuestions();
//        deleteAllWines();
//        deleteAllScores();
//        initQuestions();
//        initWines();
    }

    //Getters
    public boolean isGameActive() { return mIsGameActive; }
    public int getChosenType() { return mChosenType; }
    public List<Wine> getSelectedWines() { return mSelectedWines; }
    public Question getSelectedQuestion() { return mSelectedQuestion; }
    public Wine getAnswer() { return mAnswerWine; }
    public int getScore() { return mScore; }
    public boolean isQuestionListItemClickable() { return mQuestionListItemClickable; }
    public boolean isWineListItemClickable() { return mWineListItemClickable; }
    public ArrayList<Integer> getGameState() { return mGameState; }

    //Setters
    public void setIsGameActive(boolean isGameActive) { mIsGameActive = isGameActive; }
    public void setChosenType(int chosenType) { mChosenType = chosenType; }

    public void setTODOQuestions(List<Question> list){
        mTODOAllQuestions.clear();
        mTODOAllQuestions.addAll(list);
    }
    public void setTODOWines(List<Wine> list){
        mTODOAllWines.clear();
        mTODOAllWines.addAll(list);
    }
    public void setTODOScores(List<Score> list){
        mTODOAllScores.clear();
        mTODOAllScores.addAll(list);
    }

    public void generateQuestion() {
        mSelectedQuestion = null;
        Random r = new Random();

        while (mSelectedQuestion == null) {
            Question possibleQuestion = mTODOAllQuestions.get(r.nextInt(mTODOAllQuestions.size()));
            Log.d(TAG, "generateQuestion: help");
            if (mChosenType == NAMES && possibleQuestion.getType().equals("name")) {
                mSelectedQuestion = possibleQuestion;
            }
            else if (mChosenType == CATEGORIES && possibleQuestion.getType().equals("category")) {
                mSelectedQuestion = possibleQuestion;
            }
            else if (mChosenType == PRICES && (possibleQuestion.getType().equals("glass") || possibleQuestion.getType().equals("bottle"))) {
                mSelectedQuestion = possibleQuestion;
            }
            else if (mChosenType == ALL) {
                mSelectedQuestion = possibleQuestion;
            }

        }


    }
    public void generateAnswerAndChoices() {
        //Add three random wines with different categories to an empty CurrentQuestionWines List
        mSelectedWines.clear();
        Random r = new Random();

        //Add first wine
        mSelectedWines.add(mTODOAllWines.get(r.nextInt(mTODOAllWines.size())));

        //Add second and third wines
        int winesAdded = 1;
        while (winesAdded < 3) {
            int randomWineIndex = r.nextInt(mTODOAllWines.size());
            Wine possibleWine = mTODOAllWines.get(randomWineIndex);

            if (!doesSelectedWineListContain(mSelectedQuestion.getType(), possibleWine)) {
                mSelectedWines.add(mTODOAllWines.get(randomWineIndex));
                winesAdded++;
            }

        }

        //Shuffle the list to make it more randomized
        Collections.shuffle(mSelectedWines);

        //Choosing an answer at random out of the three wines that were selected
        if(!mSelectedWines.isEmpty()){
            mAnswerWine = mSelectedWines.get(new Random().nextInt(mSelectedWines.size()));
            mGameState.set(4,mSelectedWines.indexOf(mAnswerWine));
        }
    }
    private boolean doesSelectedWineListContain(String type, Wine possibleWine) {

        if (mSelectedWines.contains(possibleWine)) {
            return true;
        }

        for (Wine selectedWine : mSelectedWines) {
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
                    throw new IllegalStateException("Unexpected value: " + mSelectedQuestion.getType());
            }
        }

        return false;
    }
    public void setWineListItemClickable(boolean isWineListItemClickable) { mWineListItemClickable = isWineListItemClickable; }
    public void setQuestionListItemClickable(boolean isQuestionListItemClickable) { mQuestionListItemClickable = isQuestionListItemClickable; }

    //
    public boolean checkAnswer(String chosenAnswer) {
        //Reduce the number of lives by one
        switch (mSelectedQuestion.getType()) {
            case "name":
                if (mAnswerWine.getName().equals(chosenAnswer)) {
                    mScore ++;
                    return true;
                } else {
                    mGameState.set(5,mGameState.get(5)-1);
                    return false;
                }
            case "category":
                if (mAnswerWine.getCategory().equals(chosenAnswer)) {
                    mScore ++;
                    return true;
                } else {
                    mGameState.set(5,mGameState.get(5)-1);
                    return false;
                }
            case "glass":
                if (mAnswerWine.getGlassPrice() == parseFloat(chosenAnswer)) {
                    mScore ++;
                    return true;
                } else {
                    mGameState.set(5,mGameState.get(5)-1);
                    return false;
                }
            case "bottle":
                if (mAnswerWine.getBottlePrice() == parseFloat(chosenAnswer)) {
                    mScore ++;
                    return true;
                } else {
                    mGameState.set(5,mGameState.get(5)-1);
                    return false;
                }
            default:
                throw new IllegalStateException("Unexpected value: " + mSelectedQuestion.getType());
        }
    }

    public boolean saveScoreToDatabase(String name, int score) {
        Score possibleHighScore = new Score(score, name, 1, mChosenType);
        int place = mTODOAllScores.size() + 1;

        if (mTODOAllScores.isEmpty()) {
            possibleHighScore.setPlace(1);
            insert(possibleHighScore);
            return true;
        } else {
            //Find out if score is good enough to be saved
            for (Score savedScore : mTODOAllScores) {
                //check to see what type the score is and filter on that then
                if (savedScore.getType() == mChosenType) {
                    //check to see if this score is higher than any of the others
                    if (possibleHighScore.getScore() > savedScore.getScore()) {
                        place--;
                    }
                }
            }


            if (place < 4) {
                //If it is good enough to be saved then grab that current place id and set it to
                //the new score so it gets replaced in the dao insert method
                for (Score savedScore : mTODOAllScores) {
                    if (savedScore.getPlace() == place) {
                        possibleHighScore.setId(savedScore.getId());
                    }
                }


                possibleHighScore.setPlace(place);

                insert(possibleHighScore);
                return true;
            } else {
                return false;
            }
        }


    }
    //Initialize
    private void initQuestions() {
        //Name
        insert(new Question("name", "Which one of these wines is a #category!?"));
        insert(new Question("name", "Which of these wines is sold at $#glass! the glass?"));
        insert(new Question("name", "Which of these wines is sold at $#bottle! the bottle?"));

        //Category
        insert(new Question("category", "What category of wine is #name!?"));
        insert(new Question("category", "Which of these categories does #name! belong to?"));

        //Bottle
        insert(new Question("bottle", "What is the bottle price of #name!?"));
        insert(new Question("bottle", "How much is a bottle of #name!?"));

        //Glass
        insert(new Question("glass", "What is the glass price of #name!?"));
        insert(new Question("glass", "How much is the glass of #name!?"));
    }
    private void initWines() {
        //House
        insert(new Wine("Frontera Chardonnay", "House", 8, 0));
        insert(new Wine("Frontera Carmenere", "House", 8, 0));
        insert(new Wine("Sangria", "House", 7, 0));
        insert(new Wine("Placido Pinot Grigio", "House", 8, 0));

        //Sparkling
        insert(new Wine("Maschio Prosecco", "Sparkling", 0, 8));
        insert(new Wine("Maschio Rose Sparkling", "Sparkling", 0, 8));
        insert(new Wine("La Marca Prosecco", "Sparkling", 0, 30));
        insert(new Wine("Ruffino Sparkling Rose", "Sparkling", 0, 30));
        insert(new Wine("Ruffino Prosecco", "Sparkling", 0, 33));
        insert(new Wine("Chandon Brut", "Sparkling", 0, 48));

        //Pinot Grigio
        insert(new Wine("Placido", "Pinot Grigio", 8, 25));
        insert(new Wine("Estancia", "Pinot Grigio", 8, 25));

        //Sauvignon Blanc
        insert(new Wine("Nimbus", "Sauvignon Blanc", 9, 30));
        insert(new Wine("Kim Crawford", "Sauvignon Blanc", 11, 44));

        //Chardonnay
        insert(new Wine("Meiomi", "Chardonnay", 10, 38));
        insert(new Wine("Rodney Strong Chalk Hill", "Chardonnay", 0, 36));
        insert(new Wine("Kendall-Jackson 'Vintner's reserve'", "Chardonnay", 11, 36));
        insert(new Wine("Ferrari-Carrano", "Chardonnay", 13, 44));
        insert(new Wine("Rombauer", "Chardonnay", 0, 75));

        //Other Whites
        insert(new Wine("Matua Valley Pinot Noir Rose", "Other Whites", 8, 33));
        insert(new Wine("Bieler Rose", "Other Whites", 9, 35));
        insert(new Wine("Meiomi Rose", "Other Whites", 0, 40));
        insert(new Wine("Miraval Provence Rose", "Other Whites", 0, 46));
        insert(new Wine("Conundrum By Caymus", "Other Whites", 12, 44));
        insert(new Wine("Blindfold", "Other Whites", 0, 48));

        //Malbec
        insert(new Wine("Dineño", "Malbec", 9, 34));
        insert(new Wine("Trivento 'Amado Sur'", "Malbec", 0, 40));
        insert(new Wine("Pascual Toso Reserve", "Malbec", 13, 42));
        insert(new Wine("Gascon Reserve", "Malbec", 0, 50));

        //Pinot Noir
        insert(new Wine("Mark West", "Pinot Noir", 9, 33));
        insert(new Wine("Meiomi", "Pinot Noir", 12, 44));
        insert(new Wine("Diora", "Pinot Noir", 0, 50));

        //Red Blend
        insert(new Wine("Ravage", "Red Blend", 8, 32));
        insert(new Wine("19 Crimes", "Red Blend", 8, 32));
        insert(new Wine("Noble Vines The One", "Red Blend", 0, 32));
        insert(new Wine("Bogle Phantom", "Red Blend", 0, 45));
        insert(new Wine("The Prisoner", "Red Blend", 0, 75));

        //Merlot
        insert(new Wine("Robert Mondavi 'Private Selection'", "Merlot", 8, 30));

        //Cabernet Sauvignon
        insert(new Wine("Robert Mondavi 'Private Selection'", "Cabernet Sauvignon", 8, 30));
        insert(new Wine("Ravage", "Cabernet Sauvignon", 0, 30));
        insert(new Wine("Tom Gore", "Cabernet Sauvignon", 10, 35));
        insert(new Wine("Louis Martini", "Cabernet Sauvignon", 0, 38));
        insert(new Wine("Kendall-Jackson 'Vintner's Reserve", "Cabernet Sauvignon", 12, 40));
        insert(new Wine("Franciscan", "Cabernet Sauvignon", 0, 58));
        insert(new Wine("Ferrari-Carrano", "Cabernet Sauvignon", 0, 78));
        insert(new Wine("Jordan", "Cabernet Sauvignon", 0, 115));
        insert(new Wine("Silver Oak", "Cabernet Sauvignon", 0, 143));
        insert(new Wine("Caymus", "Cabernet Sauvignon", 0, 165));
    }

    //Database Querying
    public void insert(Wine wine) { mRepository.insert(wine); }
    public void insert(Question question) { mRepository.insert(question); }
    public void insert(Score score) { mRepository.insert(score); }

    public void update(Wine wine) { mRepository.update(wine); }
    public void update(Question question) {mRepository.update(question);}
    public void update(Score score) { mRepository.update(score); }

    public void delete(Wine wine) { mRepository.delete(wine); }
    public void delete(Question question) { mRepository.delete(question); }
    public void delete(Score score) { mRepository.delete(score); }

    public void deleteAllWines() { mRepository.deleteAllWines(); }
    public void deleteAllQuestions() { mRepository.deleteAllQuestions(); }
    public void deleteAllScores() { mRepository.deleteAllScores(); }

    //LiveData
    public LiveData<List<Wine>> getAllWines() { return mAllWines; }
    public LiveData<List<Question>> getAllQuestions() { return mAllQuestions; }
    public LiveData<List<Score>> getAllScores() { return mAllScores; }
}
