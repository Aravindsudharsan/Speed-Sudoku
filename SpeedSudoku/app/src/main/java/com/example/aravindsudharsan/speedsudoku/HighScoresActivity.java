package com.example.aravindsudharsan.speedsudoku;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;


public class HighScoresActivity extends MainActivity {

    static final String PREF_NAME="MyPrefFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        int min;
        int sec;
        SharedPreferences mypref=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        String temp="";
        TextView t1=findViewById(R.id.textView3);
        temp=mypref.getString("HighScoreEasy","");
        if(temp=="")
        {
            t1.setText("EASY  Empty" );
        }
        else {
            min = Integer.parseInt(temp) / 60;
            sec = Integer.parseInt(temp) % 60;
            t1.setText("EASY  " + min + ":" + String.format("%02d", sec));
        }
        TextView t2=findViewById(R.id.textView4);
        temp=mypref.getString("HighScoreMedium","");
        if(temp=="")
        {
            t2.setText("MEDIUM  Empty" );
        }
        else {
            min = Integer.parseInt(temp) / 60;
            sec = Integer.parseInt(temp) % 60;
            t2.setText("MEDIUM  " + min + ":" + String.format("%02d", sec));
        }
        TextView t3=findViewById(R.id.textView5);
        temp=mypref.getString("HighScoreHard","");
        if(temp=="")
        {
            t3.setText("HARD  Empty" );
        }
        else {
            min = Integer.parseInt(temp) / 60;
            sec = Integer.parseInt(temp) % 60;
            t3.setText("HARD  " + min + ":" + String.format("%02d", sec));
        }

    }

}
