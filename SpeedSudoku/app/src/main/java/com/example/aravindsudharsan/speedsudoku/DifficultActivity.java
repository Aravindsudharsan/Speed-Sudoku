package com.example.aravindsudharsan.speedsudoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class DifficultActivity extends MainActivity {
    Button b6;
    Button b7;
    Button b8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(DifficultActivity.this,Puzzle.class);
            intent.putExtra("Difficulty",1);
            startActivity(intent);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DifficultActivity.this,Puzzle.class);
                intent.putExtra("Difficulty",2);
                startActivity(intent);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DifficultActivity.this,Puzzle.class);
                intent.putExtra("Difficulty",3);
                startActivity(intent);
            }
        });
    }
}
