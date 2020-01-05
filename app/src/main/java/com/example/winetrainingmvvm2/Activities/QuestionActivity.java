package com.example.winetrainingmvvm2.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.winetrainingmvvm2.Adapters.OnItemClickListener;
import com.example.winetrainingmvvm2.Adapters.QuestionAdapter;
import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.R;
import com.example.winetrainingmvvm2.ViewModels.ViewModel2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.winetrainingmvvm2.Constants.Constants.FALSE;
import static com.example.winetrainingmvvm2.Constants.Constants.TRUE;

public class QuestionActivity extends AppCompatActivity implements OnItemClickListener {
    private static final String TAG = "ListActivity";

    private ViewModel2 mViewModel;
    private QuestionAdapter mQuestionAdapter;

    boolean mIsNewItem;
    int mEditItemId;

    FloatingActionButton actionButtonNew;
    FloatingActionButton actionButtonCancel;
    FloatingActionButton actionButtonSave;
    TextView tvQuestion;
    TextView tvType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setSupportActionBar((androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvQuestion = findViewById(R.id.tv_question_);
        tvType = findViewById(R.id.tv_type_);

        actionButtonNew = findViewById(R.id.action_button);
        actionButtonCancel = findViewById(R.id.action_button_cancel);
        actionButtonSave = findViewById(R.id.action_button_save);

        initRecyclerView();
        initViewModel();
        initActionButtons();

    }

    private void initActionButtons() {
        actionButtonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuestionActivity.this, "Entered Item Creation Mode", Toast.LENGTH_SHORT).show();
                mIsNewItem = true;
                openNewItemInterface();
                actionButtonNew.setVisibility(View.GONE);
                tvType.requestFocus();
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        tvType.getApplicationWindowToken(),
                        InputMethodManager.SHOW_IMPLICIT, 0);

            }
        });

        actionButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeNewInterface();
            }
        });

        actionButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the info was saved correctly then reset the database otherwise do not reset it
                if (saveInfoToDatabase()) {
                    closeNewInterface();
                }
            }
        });
    }

    private void initRecyclerView() {
        mQuestionAdapter = new QuestionAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mQuestionAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.delete(mQuestionAdapter.getQuestionAt(viewHolder.getAdapterPosition()));
                Toast.makeText(QuestionActivity.this, "Question Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ViewModel2.class);

        mViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                if(questions != null){
                    mQuestionAdapter.setQuestionList(questions);
                }
            }
        });

    }

    private void openNewItemInterface() {
        CardView cardViewLayout = findViewById(R.id.cardView_layout);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        cardViewLayout.setVisibility(View.VISIBLE);
        recyclerView.setAlpha((float) 0.5);
        mViewModel.getGameState().set(10,FALSE);
    }

    private void setOldItemInfo(Question question) {
        mEditItemId = question.getId();
        tvQuestion.setText(question.getQuestion());
        tvType.setText(question.getType());

    }

    private boolean saveInfoToDatabase() {
        if (tvType.getText().toString().equals("") || tvQuestion.getText().toString().equals("")){
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!tvType.getText().toString().equals("name") &&
                !tvType.getText().toString().equals("category") &&
                !tvType.getText().toString().equals("glass") &&
                !tvType.getText().toString().equals("bottle") ){

                    Toast.makeText(this,
                    "Please make sure you use one of the four types all lowercase with no spaces",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!tvQuestion.getText().toString().contains("name") &&
                !tvQuestion.getText().toString().contains("category") &&
                !tvQuestion.getText().toString().contains("glass") &&
                !tvQuestion.getText().toString().contains("bottle") ){

                    Toast.makeText(this,
                    "Please make sure you use one of the four types all lowercase in your question",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            Question newQuestion = new Question(tvType.getText().toString(), tvQuestion.getText().toString());

            if (mIsNewItem) {
                mViewModel.insert(newQuestion);
                Toast.makeText(this, "Item Inserted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Item Updated", Toast.LENGTH_SHORT).show();
                newQuestion.setId(mEditItemId);
                mViewModel.update(newQuestion);
            }
            return true;
        }

    }

    private void closeNewInterface() {
        CardView cardViewLayout = findViewById(R.id.cardView_layout);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        cardViewLayout.setVisibility(View.GONE);
        recyclerView.setAlpha(1);
        mViewModel.getGameState().set(10,TRUE);

        actionButtonNew.setVisibility(View.VISIBLE);
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                actionButtonNew.getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS, 0);

        tvQuestion.setText("");
        tvType.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.delete_all_wines:
//                mViewModel.deleteAllNotes();
//                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onItemClick(int position) {
        if(mViewModel.getGameState().get(10) == TRUE){
            Toast.makeText(QuestionActivity.this, "Entered Edit Item Mode", Toast.LENGTH_SHORT).show();
            mIsNewItem = false;
            openNewItemInterface();
            setOldItemInfo(mQuestionAdapter.getQuestionAt(position));
        }
    }
}
