package com.example.winetrainingmvvm2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Button btnAnswer1 = findViewById(R.id.btn_answer1);
        Button btnAnswer2 = findViewById(R.id.btn_answer2);
        Button btnAnswer3 = findViewById(R.id.btn_answer3);


    }
}
