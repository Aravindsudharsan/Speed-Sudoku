package com.example.aravindsudharsan.speedsudoku;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button b;
    Button b1;
    Button b2;
    int SIGN_IN_REQUEST_CODE=1;
    ConstraintLayout activity_main;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(getApplicationContext(), "onOptionsItemSelected() calledz",Toast.LENGTH_SHORT).show();
        //Get references to the views of list_item.xml
        if(item.getItemId() == R.id.menu_sign_out)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activity_main,"You have been signed out.", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Toast.makeText(getApplicationContext(), "onCreateOptionsMenu() calledz",Toast.LENGTH_SHORT).show();
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Toast.makeText(getApplicationContext(), "onActivityResult() calledz",Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Snackbar.make(activity_main,"Successfully signed in.Welcome!", Snackbar.LENGTH_SHORT).show();
            }
            else{
                Snackbar.make(activity_main,"We couldn't sign you in.Please try again later", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            Firebase.setAndroidContext(getApplicationContext());
        }
        catch (Exception e) {
            Log.e("Firebase","Init not working");
        }
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),1);
        }

        b = (Button) findViewById(R.id.button);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button4);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ModeActivity.class);
                startActivity(i);
                //setContentView(R.layout.activity_mode);

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), InstructionActivity.class);
                startActivity(i);
                //setContentView(R.layout.activity_instruction);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HighScoresActivity.class);
                startActivity(i);
                //setContentView(R.layout.activity_instruction);
            }
        });
    }
}

