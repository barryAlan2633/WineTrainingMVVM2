package com.example.winetrainingmvvm2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.winetrainingmvvm2.Adapters.OnItemClickListener;
import com.example.winetrainingmvvm2.Adapters.WineAdapter;
import com.example.winetrainingmvvm2.Models.Wine;
import com.example.winetrainingmvvm2.R;
import com.example.winetrainingmvvm2.ViewModels.ViewModel2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.winetrainingmvvm2.Constants.Constants.FALSE;
import static com.example.winetrainingmvvm2.Constants.Constants.TRUE;

public class ListActivity extends AppCompatActivity implements OnItemClickListener {
    private static final String TAG = "ListActivity";

    private ViewModel2 mWineViewModel;
    private WineAdapter mWineAdapter;

    boolean mIsNewItem;
    int mEditItemId;

    FloatingActionButton actionButtonNew;
    FloatingActionButton actionButtonCancel;
    FloatingActionButton actionButtonSave;
    TextView name;
    TextView category;
    TextView glassPrice;
    TextView bottlePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setSupportActionBar((androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        category = findViewById(R.id.category);
        glassPrice = findViewById(R.id.glassPrice);
        bottlePrice = findViewById(R.id.bottlePrice);
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
                Toast.makeText(ListActivity.this, "Entered Item Creation Mode", Toast.LENGTH_SHORT).show();
                mIsNewItem = true;
                openNewItemInterface();
                actionButtonNew.setVisibility(View.GONE);
                name.requestFocus();
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        name.getApplicationWindowToken(),
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
        mWineAdapter = new WineAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mWineAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mWineViewModel.delete(mWineAdapter.getWineAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ListActivity.this, "Wine Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void initViewModel() {
        mWineViewModel = ViewModelProviders.of(this).get(ViewModel2.class);

        mWineViewModel.getAllWines().observe(this, new Observer<List<Wine>>() {
            @Override
            public void onChanged(List<Wine> wines) {
                if(wines != null){
                    mWineAdapter.setWineList(wines);
                }
            }
        });

    }

    private void openNewItemInterface() {
        CardView cardViewLayout = findViewById(R.id.cardView_layout);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        cardViewLayout.setVisibility(View.VISIBLE);
        recyclerView.setAlpha((float) 0.5);
        mWineViewModel.getGameState().set(9,FALSE);
    }

    private void setOldItemInfo(Wine wine) {
        mEditItemId = wine.getId();
        name.setText(wine.getName());
        category.setText(wine.getCategory());
        glassPrice.setText(Float.toString(wine.getGlassPrice()));
        bottlePrice.setText(Float.toString(wine.getBottlePrice()));

    }

    private boolean saveInfoToDatabase() {
        if (name.getText().toString().equals("") || category.getText().toString().equals("") ||
                glassPrice.getText().toString().equals("") || bottlePrice.getText().toString().equals("")) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            Wine newWine = new Wine(name.getText().toString(),
                    category.getText().toString(),
                    Float.parseFloat(glassPrice.getText().toString()),
                    Float.parseFloat(bottlePrice.getText().toString()));

            if(mIsNewItem){
                mWineViewModel.insert(newWine);
                Toast.makeText(this, "Item Inserted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Item Updated", Toast.LENGTH_SHORT).show();
                newWine.setId(mEditItemId);
                mWineViewModel.update(newWine);
            }
            return true;
        }

    }

    private void closeNewInterface() {
        CardView cardViewLayout = findViewById(R.id.cardView_layout);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        cardViewLayout.setVisibility(View.GONE);
        recyclerView.setAlpha(1);
        mWineViewModel.getGameState().set(9,TRUE);


        actionButtonNew.setVisibility(View.VISIBLE);
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                actionButtonNew.getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS, 0);

        name.setText("");
        category.setText("");
        glassPrice.setText("");
        bottlePrice.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_list, menu);

        return true;
    }


    @Override
    public void onItemClick(int position) {
        if(mWineViewModel.getGameState().get(9) == TRUE){
            Toast.makeText(ListActivity.this, "Entered Edit Item Mode", Toast.LENGTH_SHORT).show();
            mIsNewItem = false;
            openNewItemInterface();
            setOldItemInfo(mWineAdapter.getWineAt(position));
        }
    }
}
