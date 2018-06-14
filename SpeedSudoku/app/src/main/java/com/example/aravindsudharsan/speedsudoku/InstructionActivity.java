package com.example.aravindsudharsan.speedsudoku;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;



public class InstructionActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        Locale locale = getResources().getConfiguration().locale;
        System.out.println(locale);

        Resources res = getBaseContext().getResources();
        res.updateConfiguration(new Configuration(), res.getDisplayMetrics());

        locale = getResources().getConfiguration().locale;
        System.out.println(locale);
    }

}
