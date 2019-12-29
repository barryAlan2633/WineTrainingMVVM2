package com.example.winetrainingmvvm2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.Models.Score;
import com.example.winetrainingmvvm2.Models.Wine;
import com.example.winetrainingmvvm2.Repositories.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ViewModel extends AndroidViewModel {
    private static final String TAG = "ViewModel";
    private static int mChosenType = -1;
    private static int mScore = 0;
    private static boolean mWineListItemClickable = true;
    private static boolean mQuestionListItemClickable = true;
    private static int mNumberOfQuestions;
    private Wine mAnswerWine = null;
    private List<Wine> mSelectedWines = new ArrayList<>();
    private List<Question> mSelectedQuestion = new ArrayList<>();

    //Todo some type of checking to see what is selected on the alertview

    private Repository mRepository;
    private LiveData<List<Wine>> mAllWines;
    private LiveData<List<Question>> mAllQuestions;
    private LiveData<List<Score>> mAllScores;


    //Constructor
    public ViewModel(@NonNull Application application) {
        super(application);
        mScore = 0;
        mNumberOfQuestions = 10;
        this.mRepository = new Repository(application);
        this.mAllWines = mRepository.getAllWines();
        this.mAllQuestions = mRepository.getAllQuestions();
        this.mAllScores = mRepository.getAllScores();
//        deleteAllQuestions();
//        deleteAllWines();
//        initQuestions();
//        initWines();
    }

    private void initQuestions() {
        //Name
        insert(new Question("name","Which one of these wines is a #category!?"));
        insert(new Question("name","Which of these wines is sold at $#glass! the glass?"));
        insert(new Question("name","Which of these wines is sold at $#bottle! the bottle?"));

        //Category
        insert(new Question("category","What category of wine is #name!?"));
        insert(new Question("category","Which of these categories does #name! belong to?"));

        //Bottle
        insert(new Question("bottle","What is the bottle price of #name!?"));
        insert(new Question("bottle","How much is a bottle of #name!?"));

        //Glass
        insert(new Question("glass","What is the glass price of #name!?"));
        insert(new Question("glass","How much is the glass of #name!?"));
    }

    private void initWines() {
        //House
        insert(new Wine("Frontera Chardonnay","House",8,0));
        insert(new Wine("Frontera Carmenere","House",8,0));
        insert(new Wine("Sangria","House",7,0));
        insert(new Wine("Placido Pinot Grigio","House",8,0));

        //Sparkling
        insert(new Wine("Maschio Prosecco","Sparkling",0,8));
        insert(new Wine("Maschio Rose Sparkling","Sparkling",0,8));
        insert(new Wine("La Marca Prosecco","Sparkling",0,30));
        insert(new Wine("Ruffino Sparkling Rose","Sparkling",0,30));
        insert(new Wine("Ruffino Prosecco","Sparkling",0,33));
        insert(new Wine("Chandon Brut","Sparkling",0,48));

        //Pinot Grigio
        insert(new Wine("Placido","Pinot Grigio",8,25));
        insert(new Wine("Estancia","Pinot Grigio",8,25));

        //Sauvignon Blanc
        insert(new Wine("Nimbus","Sauvignon Blanc",9,30));
        insert(new Wine("Kim Crawford","Sauvignon Blanc",11,44));

        //Chardonnay
        insert(new Wine("Meiomi","Chardonnay",10,38));
        insert(new Wine("Rodney Strong Chalk Hill","Chardonnay",0,36));
        insert(new Wine("Kendall-Jackson 'Vintner's reserve'","Chardonnay",11,36));
        insert(new Wine("Ferrari-Carrano","Chardonnay",13,44));
        insert(new Wine("Rombauer","Chardonnay",0,75));

        //Other Whites
        insert(new Wine("Matua Valley Pinot Noir Rose","Other Whites",8,33));
        insert(new Wine("Bieler Rose","Other Whites",9,35));
        insert(new Wine("Meiomi Rose","Other Whites",0,40));
        insert(new Wine("Miraval Provence Rose","Other Whites",0,46));
        insert(new Wine("Conundrum By Caymus","Other Whites",12,44));
        insert(new Wine("Blindfold","Other Whites",0,48));

        //Malbec
        insert(new Wine("Dineño","Malbec",9,34));
        insert(new Wine("Trivento 'Amado Sur'","Malbec",0,40));
        insert(new Wine("Pascual Toso Reserve","Malbec",13,42));
        insert(new Wine("Gascon Reserve","Malbec",0,50));

        //Pinot Noir
        insert(new Wine("Mark West","Pinot Noir",9,33));
        insert(new Wine("Meiomi","Pinot Noir",12,44));
        insert(new Wine("Diora","Pinot Noir",0,50));

        //Red Blend
        insert(new Wine("Ravage","Red Blend",8,32));
        insert(new Wine("19 Crimes","Red Blend",8,32));
        insert(new Wine("Noble Vines The One","Red Blend",0,32));
        insert(new Wine("Bogle Phantom","Red Blend",0,45));
        insert(new Wine("The Prisoner","Red Blend",0,75));

        //Merlot
        insert(new Wine("Robert Mondavi 'Private Selection'","Merlot",8,30));

        //Cabernet Sauvignon
        insert(new Wine("Robert Mondavi 'Private Selection'","Cabernet Sauvignon",8,30));
        insert(new Wine("Ravage","Cabernet Sauvignon",0,30));
        insert(new Wine("Tom Gore","Cabernet Sauvignon",10,35));
        insert(new Wine("Louis Martini","Cabernet Sauvignon",0,38));
        insert(new Wine("Kendall-Jackson 'Vintner's Reserve","Cabernet Sauvignon",12,40));
        insert(new Wine("Franciscan","Cabernet Sauvignon",0,58));
        insert(new Wine("Ferrari-Carrano","Cabernet Sauvignon",0,78));
        insert(new Wine("Jordan","Cabernet Sauvignon",0,115));
        insert(new Wine("Silver Oak","Cabernet Sauvignon",0,143));
        insert(new Wine("Caymus","Cabernet Sauvignon",0,165));
    }


    //Play activity methods

    public void setChosenType(int chosenType){
        mChosenType = chosenType;
    }

    public int getChosenType(){
        return mChosenType;
    }

    public List<Wine> getSelectedWines() {
        return mSelectedWines;
    }

    public List<Question> getSelectedQuestion() {
        return mSelectedQuestion;
    }

    public Wine getAnswerWine() {
        return mAnswerWine;
    }

    public void setAnswerWine(){
        if(!mSelectedWines.isEmpty()){
            mAnswerWine = mSelectedWines.get(new Random().nextInt(mSelectedWines.size()));
        }
    }

    public int getScore(){
        return mScore;
    }

    public void setScore(int score){
        mScore = score;
    }

    public int getNumberOfQuestions(){
        return mNumberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions){
        mNumberOfQuestions = numberOfQuestions;
    }

    public boolean isWineListItemClickable() {
        return mWineListItemClickable;
    }

    public void setWineListItemClickable(boolean mWineListItemClickable) {
        ViewModel.mWineListItemClickable = mWineListItemClickable;
    }

    public boolean isQuestionListItemClickable() {
        return mQuestionListItemClickable;
    }

    public void setQuestionListItemClickable(boolean mQuestionListItemClickable) {
        ViewModel.mQuestionListItemClickable = mQuestionListItemClickable;
    }

    //Database Querying
    public void insert(Wine wine) {
        mRepository.insert(wine);
    }
    public void insert(Question question) {
        mRepository.insert(question);
    }
    public void insert(Score score) { mRepository.insert(score); }

    public void update(Wine wine) {
        mRepository.update(wine);
    }
    public void update(Question question) {
        mRepository.update(question);
    }
    public void update(Score score) {
        mRepository.update(score);
    }

    public void delete(Wine wine) {
        mRepository.delete(wine);
    }
    public void delete(Question question) {
        mRepository.delete(question);
    }
    public void delete(Score score) {
        mRepository.delete(score);
    }

    public void deleteAllWines() {
        mRepository.deleteAllWines();
    }
    public void deleteAllQuestions() {
        mRepository.deleteAllQuestions();
    }
    public void deleteAllScores() {
        mRepository.deleteAllScores();
    }

    //LiveData
    public LiveData<List<Wine>> getAllWines() {
        return mAllWines;
    }
    public LiveData<List<Question>> getAllQuestions() {
        return mAllQuestions;
    }
    public LiveData<List<Score>> getAllScores() { return mAllScores; }


}
