package com.example.aravindsudharsan.speedsudoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModeActivity extends MainActivity {
    Button b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DifficultActivity.class);
                startActivity(i);
                //setContentView(R.layout.activity_difficulty);

            }
        });

        b3 = (Button) findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Puzzle_multi.class);
                startActivity(i);
                //setContentView(R.layout.activity_difficulty);

            }
        });

    }
}
