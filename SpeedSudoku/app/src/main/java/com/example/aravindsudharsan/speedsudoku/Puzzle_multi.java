package com.example.aravindsudharsan.speedsudoku;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static com.google.firebase.FirebaseApp.initializeApp;

public class Puzzle_multi extends MainActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout layout_puzzle_multi;
        ProgressBar pb,pb2;
        Button b;
        TextView txtv,time;
        TextView txtp;
        TextView tv;
        RadioGroup radio_group;
        RadioButton radio_button;
        static final String PREF_NAME="MyPrefFile";
        int high_score;
        int t;
        int difficulty=1;
        int zeros;
        int active;
        int min,sec;
        int timer_run=1;
        String user="";
        int puzzle_solved=0;
        int ready=0;
        int user_active=0;
        int puzzle_solved2=0;
        int ready2=0;
        int user_active2=0;
        int puzzle_id=1;
        AlertDialog.Builder alertDialogBuilder ;

        int puzzle_all[][][]={{{0,6,0,0,0,9,0,8,5},{9,4,0,0,0,6,0,0,2},{0,8,1,0,0,4,0,0,3},
                {6,0,0,0,0,0,1,0,8},{0,0,0,0,3,0,0,0,0},{2,0,8,0,0,0,0,0,9},
                {3,0,0,1,0,0,8,4,0},{8,0,0,6,0,0,0,5,7},{4,7,0,2,0,0,0,3,0}},

                {{0,0,0,2,6,0,7,0,1},{6,8,0,0,7,0,0,9,0},{1,9,0,0,0,4,5,0,0},
                        {8,2,0,1,0,0,0,4,0},{0,0,4,6,0,2,9,0,0},{0,5,0,0,0,3,0,2,8},
                        {0,0,9,3,0,0,0,7,4},{0,4,0,0,5,0,0,3,6},{7,0,3,0,1,8,0,0,0}},

                {{1,0,0,4,8,9,0,0,6},{7,3,0,0,0,0,0,4,0},{0,0,0,0,0,1,2,9,5},
                        {0,0,7,1,2,0,6,0,0},{5,0,0,7,0,3,0,0,8},{0,0,6,0,9,5,7,0,0},
                        {9,1,4,6,0,0,0,0,0},{0,2,0,0,0,0,0,3,7},{8,0,0,5,1,2,0,0,4}},

                {{0,9,6,0,4,0,0,3,0},{0,5,7,8,2,0,0,0,0},{1,0,0,9,0,0,5,0,0},
                        {0,0,9,0,1,0,0,0,8},{5,0,0,0,0,0,0,0,2},{4,0,0,0,9,0,6,0,0},
                        {0,0,4,0,0,3,0,0,1},{0,0,0,0,7,9,2,6,0},{0,2,0,0,5,0,9,8,0}},

                {{2,9,0,0,7,4,0,0,0},{0,1,0,0,0,0,4,0,0},{6,7,0,9,0,5,0,0,0},
                        {0,8,0,2,0,6,0,0,0},{0,6,0,8,4,7,0,2,0},{0,0,0,5,0,1,0,8,0},
                        {0,0,0,7,0,8,0,9,2},{0,0,6,0,0,0,0,1,0},{0,0,0,4,1,0,0,5,8}},

                {{1,0,8,0,0,6,4,0,0},{0,0,6,0,9,0,8,0,7},{5,0,0,0,0,0,0,0,0},
                        {2,6,9,5,0,0,0,8,0},{0,0,0,4,0,9,0,0,0},{0,8,0,0,0,2,7,9,1},
                        {0,0,0,0,0,0,0,0,5},{6,0,4,0,7,0,2,0,0},{0,0,1,2,0,0,9,0,3}},

                {{4,0,6,0,2,0,0,0,0},{0,8,0,4,0,0,0,9,3},{3,0,0,0,8,5,0,0,2},
                        {7,0,9,0,0,0,0,0,8},{0,5,0,0,7,0,0,4,0},{6,0,0,0,0,0,7,0,1},
                        {9,0,0,2,4,0,0,0,5},{2,6,0,0,0,8,0,7,0},{0,0,0,0,3,0,1,0,9}},

                {{0,0,0,2,4,0,6,0,0},{9,0,0,0,0,0,0,0,3},{1,0,0,0,0,3,0,4,5},
                        {5,6,0,0,7,0,1,0,0},{0,0,4,8,0,5,9,0,0},{0,0,1,0,6,0,0,5,2},
                        {6,9,0,5,0,0,0,0,1},{4,0,0,0,0,0,0,0,9},{0,0,8,0,9,6,0,0,0}},

                {{0,0,0,7,0,9,0,2,0},{0,0,9,2,1,6,0,0,5},{5,0,0,8,0,4,0,0,0},
                        {0,6,0,0,0,0,4,0,0},{3,7,0,0,4,0,0,6,1},{0,0,2,0,0,0,0,5,0},
                        {0,0,0,9,0,7,0,0,3},{7,0,0,3,8,5,2,0,0},{0,3,0,4,0,1,0,0,0}},

                {{0,2,0,6,0,8,0,0,0},{5,8,0,0,0,9,7,0,0},{0,0,0,0,4,0,0,0,0},
                        {3,7,0,0,0,0,5,0,0},{6,0,0,0,0,0,0,0,4},{0,0,8,0,0,0,0,1,3},
                        {0,0,0,0,2,0,0,0,0},{0,0,9,8,0,0,0,3,6},{0,0,0,3,0,6,0,9,0}},

                {{0,0,0,0,0,0,8,0,6},{4,0,5,6,9,0,0,1,0},{0,0,9,0,0,2,4,0,0},
                        {5,0,0,0,0,3,0,8,0},{0,0,7,8,0,9,6,0,0},{0,9,0,2,0,0,0,0,3},
                        {0,0,4,7,0,0,1,0,0},{0,6,0,0,4,1,7,0,8},{7,0,3,0,0,0,0,0,0}},

                {{0,0,8,0,0,7,0,0,0},{5,0,0,0,0,0,7,0,1},{9,2,0,1,0,0,0,3,6},
                        {0,0,0,8,7,2,0,0,5},{0,0,9,0,0,0,3,0,0},{1,0,0,9,5,3,0,0,0},
                        {3,7,0,0,0,9,0,4,8},{2,0,6,0,0,0,0,0,9},{0,0,0,7,0,0,2,0,0}},

                {{0,0,4,2,0,8,6,0,0},{0,0,0,0,9,0,0,0,5},{0,8,0,0,4,0,0,1,7},
                        {0,6,0,0,0,9,5,0,0},{0,0,9,7,6,5,8,0,0},{0,0,5,4,0,0,0,6,0},
                        {6,4,0,0,2,0,0,5,0},{3,0,0,0,8,0,0,0,0},{0,0,2,3,0,6,1,0,0}},

                {{9,0,5,0,0,1,0,0,0},{4,0,3,9,0,0,0,5,0},{0,8,0,7,5,0,0,0,0},
                        {0,5,1,0,0,0,0,0,3},{8,4,0,0,0,0,0,7,6},{6,0,0,0,0,0,1,8,0},
                        {0,0,0,0,9,6,0,1,0},{0,9,0,0,0,3,8,0,7},{0,0,0,1,0,0,5,0,9}},

                {{0,0,0,6,0,0,4,0,0},{7,0,0,0,0,3,6,0,0},{0,0,0,0,9,1,0,8,0},
                        {0,0,0,0,0,0,0,0,0},{0,5,0,1,8,0,0,0,3},{0,0,0,3,0,6,0,4,5},
                        {0,4,0,2,0,0,0,6,0},{9,0,3,0,0,0,0,0,0},{0,2,0,0,0,0,1,0,0}}};

        int solved_all[][][]={{{7,6,2,3,1,9,4,8,5},{9,4,3,5,8,6,7,1,2},{5,8,1,7,2,4,6,9,3},
                {6,3,4,9,7,5,1,2,8},{1,9,7,8,3,2,5,6,4},{2,5,8,4,6,1,3,7,9},
                {3,2,5,1,9,7,8,4,6},{8,1,9,6,4,3,2,5,7},{4,7,6,2,5,8,9,3,1}},

                {{4,3,5,2,6,9,7,8,1},{6,8,2,5,7,1,4,9,3},{1,9,7,8,3,4,5,6,2},
                        {8,2,6,1,9,5,3,4,7},{3,7,4,6,8,2,9,1,5},{9,5,1,7,4,3,6,2,8},
                        {5,1,9,3,2,6,8,7,4},{2,4,8,9,5,7,1,3,6},{7,6,3,4,1,8,2,5,9}},

                {{1,5,2,4,8,9,3,7,6},{7,3,9,2,5,6,8,4,1},{4,6,8,3,7,1,2,9,5},
                        {3,8,7,1,2,4,6,5,9},{5,9,1,7,6,3,4,2,8},{2,4,6,8,9,5,7,1,3},
                        {9,1,4,6,3,7,5,8,2},{6,2,5,9,4,8,1,3,7},{8,7,3,5,1,2,9,6,4}},

                {{2,9,6,1,4,5,8,3,7},{3,5,7,8,2,6,1,4,9},{1,4,8,9,3,7,5,2,6},
                        {6,3,9,5,1,2,4,7,8},{5,8,1,7,6,4,3,9,2},{4,7,2,3,9,8,6,1,5},
                        {9,6,4,2,8,3,7,5,1},{8,1,5,4,7,9,2,6,3},{7,2,3,6,5,1,9,8,4}},

                {{2,9,3,1,7,4,8,6,5},{5,1,8,6,2,3,4,7,9},{6,7,4,9,8,5,2,3,1},
                        {3,8,5,2,9,6,1,4,7},{1,6,9,8,4,7,5,2,3},{7,4,2,5,3,1,9,8,6},
                        {4,5,1,7,6,8,3,9,2},{8,2,6,3,5,9,7,1,4},{9,3,7,4,1,2,6,5,8}},

                {{1,9,8,7,5,6,4,3,2},{3,2,6,1,9,4,8,5,7},{5,4,7,3,2,8,1,6,9},
                        {2,6,9,5,1,7,3,8,4},{7,1,3,4,8,9,5,2,6},{4,8,5,6,3,2,7,9,1},
                        {9,3,2,8,4,1,6,7,5},{6,5,4,9,7,3,2,1,8},{8,7,1,2,6,5,9,4,3}},

                {{4,1,6,9,2,3,5,8,7},{5,8,2,4,1,7,6,9,3},{3,9,7,6,8,5,4,1,2},
                        {7,3,9,1,6,4,2,5,8},{1,5,8,3,7,2,9,4,6},{6,2,4,8,5,9,7,3,1},
                        {9,7,3,2,4,1,8,6,5},{2,6,1,5,9,8,3,7,4},{8,4,5,7,3,6,1,2,9}},

                {{8,5,3,2,4,1,6,9,7},{9,4,2,6,5,7,8,1,3},{1,7,6,9,8,3,2,4,5},
                        {5,6,9,4,7,2,1,3,8},{2,3,4,8,1,5,9,7,6},{7,8,1,3,6,9,4,5,2},
                        {6,9,7,5,2,4,3,8,1},{4,2,5,1,3,8,7,6,9},{3,1,8,7,9,6,5,2,4}},

                {{6,1,3,7,5,9,8,2,4},{4,0,9,2,1,6,3,7,5},{5,2,7,8,3,4,6,1,9},
                        {9,6,5,1,7,8,4,3,2},{3,7,8,5,4,2,9,6,1},{1,4,2,6,9,3,7,5,8},
                        {2,5,4,9,6,7,1,8,3},{7,9,1,3,8,5,2,4,6},{8,3,6,4,2,1,5,9,7}},

                {{1,2,3,6,7,8,9,4,5},{5,8,4,2,3,9,7,6,1},{9,6,7,1,4,5,3,2,8},
                        {3,7,2,4,6,1,5,8,9},{6,9,1,5,8,3,2,7,4},{4,5,8,7,9,2,6,1,3},
                        {8,3,6,9,2,4,1,5,7},{2,1,9,8,5,7,4,3,6},{7,4,5,3,1,6,8,9,2}},

                {{2,3,1,5,7,4,8,9,6},{4,7,5,6,9,8,3,1,2},{6,8,9,1,3,2,4,7,5},
                        {5,2,6,4,1,3,9,8,7},{3,4,7,8,5,9,6,2,1},{1,9,8,2,6,7,5,4,3},
                        {8,5,4,7,2,6,1,3,9},{9,6,2,3,4,1,7,5,8},{7,1,3,9,8,5,2,6,4}},

                {{6,1,8,2,3,7,9,5,4},{5,3,4,6,9,8,7,2,1},{9,2,7,1,4,5,8,3,6},
                        {4,6,3,8,7,2,1,9,5},{7,5,9,4,1,6,3,8,2},{1,8,2,9,5,3,4,6,7},
                        {3,7,1,5,2,9,6,4,8},{2,4,6,3,8,1,5,7,9},{8,9,5,7,6,4,2,1,3}},

                {{5,1,4,2,7,8,6,9,3},{2,3,7,6,9,1,4,8,5},{9,8,6,5,4,3,2,1,7},
                        {4,6,3,8,1,9,5,7,2},{1,2,9,7,6,5,8,3,4},{8,7,5,4,3,2,9,6,1},
                        {6,4,8,1,2,7,3,5,9},{3,5,1,9,8,4,7,2,6},{7,9,2,3,5,6,1,4,8}},

                {{9,7,5,2,3,1,6,4,8},{4,1,3,9,6,8,7,5,2},{2,8,6,7,5,4,9,3,1},
                        {7,5,1,6,8,2,4,9,3},{8,4,9,3,1,5,2,7,6},{6,3,2,4,7,9,1,8,5},
                        {5,2,7,8,9,6,3,1,4},{1,9,4,5,2,3,8,6,7},{3,6,8,1,4,7,5,2,9}},

                {{5,8,1,6,7,2,4,3,9},{7,9,2,8,4,3,6,5,1},{3,6,4,5,9,1,7,8,2},
                        {4,3,8,9,5,7,2,1,6},{2,5,6,1,8,4,9,7,3},{1,7,9,3,2,6,8,4,5},
                        {8,4,5,2,1,9,3,6,7},{9,1,3,7,6,8,5,2,4},{6,2,7,4,3,5,1,9,8}}};


        int puzzle[][]=new int[9][9];

        int puzzle_clear[][]=new int[9][9];

        int solved[][]=new int[9][9];

        int ids[]={R.id.textView1,R.id.textView2,R.id.textView3,
                R.id.textView4,R.id.textView5,R.id.textView6,
                R.id.textView7,R.id.textView8,R.id.textView9,R.id.textView10,
                R.id.textView11,R.id.textView12,R.id.textView13,
                R.id.textView14,R.id.textView15,R.id.textView16,
                R.id.textView17,R.id.textView18,R.id.textView19,R.id.textView20,
                R.id.textView21,R.id.textView22,R.id.textView23,
                R.id.textView24,R.id.textView25,R.id.textView26,
                R.id.textView27,R.id.textView28,R.id.textView29,R.id.textView30,
                R.id.textView31,R.id.textView32,R.id.textView33,
                R.id.textView34,R.id.textView35,R.id.textView36,
                R.id.textView37,R.id.textView38,R.id.textView39,R.id.textView40,
                R.id.textView41,R.id.textView42,R.id.textView43,
                R.id.textView44,R.id.textView45,R.id.textView46,
                R.id.textView47,R.id.textView48,R.id.textView49,R.id.textView50,
                R.id.textView51,R.id.textView52,R.id.textView53,
                R.id.textView54,R.id.textView55,R.id.textView56,
                R.id.textView57,R.id.textView58,R.id.textView59,R.id.textView60,
                R.id.textView61,R.id.textView62,R.id.textView63,
                R.id.textView64,R.id.textView65,R.id.textView66,
                R.id.textView67,R.id.textView68,R.id.textView69,R.id.textView70,
                R.id.textView71,R.id.textView72,R.id.textView73,
                R.id.textView74,R.id.textView75,R.id.textView76,
                R.id.textView77,R.id.textView78,R.id.textView79,R.id.textView80,
                R.id.textView81};


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(getApplicationContext(), "onOptionsItemSelected() calledz",Toast.LENGTH_SHORT).show();
        //Get references to the views of list_item.xml
        if(item.getItemId() == R.id.menu_sign_out)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(layout_puzzle_multi,"You have been signed out.", Snackbar.LENGTH_SHORT).show();
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
                Snackbar.make(layout_puzzle_multi,"Successfully signed in.Welcome!", Snackbar.LENGTH_SHORT).show();
                sendMessage();
            }
            else{
                Snackbar.make(layout_puzzle_multi,"We couldn't sign you in.Please try again later", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_puzzle_multi);
        alertDialogBuilder= new AlertDialog.Builder(this);

            //user = FirebaseAuth.getInstance().getCurrentUser().getEmail();


            pb = findViewById(R.id.progressBar);
            pb2 = findViewById(R.id.progressBar2);
            puzzle_solved = 0;
            ready = 1;
            user_active = 1;
            try {
                Firebase.setAndroidContext(getApplicationContext());
            } catch (Exception e) {
                Log.e("Firebase", "Init not working");
            }
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
            } else {
                user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                TextView user1 = findViewById(R.id.u);
                //user1.setText(user);
                Snackbar.make(findViewById(R.id.puzzle_multi), "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Snackbar.LENGTH_SHORT).show();
                if (user.equals("vinubhagavath@ucsb.edu")) {
                    FirebaseDatabase.getInstance().getReference().child("user 1").setValue(new ChatMessage(
                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                            0,
                            0,
                            1,
                            1,
                            puzzle_id

                    ));
                    //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();
                }

                if (user.equals("vinu021295@gmail.com")) {
                    FirebaseDatabase.getInstance().getReference().child("user 2").setValue(new ChatMessage(
                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                            0,
                            0,
                            1,
                            1,
                            puzzle_id

                    ));
                    //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();
                }

                sendMessage();
            }
            findViewById(R.id.progressBar2).setVisibility(View.INVISIBLE);
            findViewById(R.id.button).setVisibility(View.INVISIBLE);
            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            findViewById(R.id.Squares).setVisibility(View.INVISIBLE);
            findViewById(R.id.time).setVisibility(View.INVISIBLE);
            findViewById(R.id.Radio_numbers).setVisibility(View.INVISIBLE);
            findViewById(R.id.gl).setVisibility(View.INVISIBLE);
            findViewById(R.id.button2).setVisibility(View.INVISIBLE);
            findViewById(R.id.u2).setVisibility(View.INVISIBLE);
            findViewById(R.id.u).setVisibility(View.INVISIBLE);
            findViewById(R.id.entry_screen).setVisibility(View.VISIBLE);
            TextView z= findViewById(R.id.entry_screen);
            z.setText("Waiting for user");


        }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (user.equals("vinubhagavath@ucsb.edu")) {
            FirebaseDatabase.getInstance().getReference().child("user 1").setValue(new ChatMessage(
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                    0,
                    0,
                    0,
                    0,
                    puzzle_id

            ));
            //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();
        }

        if (user.equals("vinu021295@gmail.com")) {
            FirebaseDatabase.getInstance().getReference().child("user 2").setValue(new ChatMessage(
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                    0,
                    0,
                    0,
                    0,
                    puzzle_id

            ));
            //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (user.equals("vinubhagavath@ucsb.edu")) {
            FirebaseDatabase.getInstance().getReference().child("user 1").setValue(new ChatMessage(
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                    0,
                    0,
                    0,
                    0,
                    puzzle_id

            ));
            //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();
        }

        if (user.equals("vinu021295@gmail.com")) {
            FirebaseDatabase.getInstance().getReference().child("user 2").setValue(new ChatMessage(
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                    0,
                    0,
                    0,
                    0,
                    puzzle_id

            ));
            //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();
        }
    }
        public int getprogress()
        {
            int progress =0;
            int count=0;
            String a;

            for (int i=0;i<9;i++) {
                for(int j=0;j<9;j++) {

                    if (puzzle[i][j] == 0)
                        count++;
                }

            }

            progress=(int)(100-(count*100/zeros));

            return progress;

        }

        public boolean check(View view)
        {

            boolean solved=true;
            int sum=0;

            for(int i=0;i<9;i++)
            {
                for (int j=0;j<9;j++)
                {
                    sum+=puzzle[i][j];
                }
                Log.i("Puzzle",String.valueOf(sum));
                if(sum!=45)
                    solved=false;
                sum=0;
            }

            for(int i=0;i<9;i++)
            {
                for (int j=0;j<9;j++)
                {
                    sum+=puzzle[j][i];
                }
                Log.i("Puzzle",String.valueOf(sum));
                if(sum!=45)
                    solved=false;
                sum=0;
            }

            for(int i=0;i<3;i++)
            {
                for (int j=0;j<3;j++)
                {
                    for(int k=0;k<3;k++)
                    {
                        for (int l=0;l<3;l++)
                        {
                            sum+=puzzle[i*3+k][j*3+l];
                        }
                    }
                    Log.i("Puzzle",String.valueOf(sum));
                    if(sum!=45)
                        solved=false;
                    sum=0;
                }
            }

            return solved;

        }
        public void onClick1(View view)
        {

            // set title


            int current_time=min*60+sec;


            if(check(view)==true)
            {
                puzzle_solved=1;
                if (user.equals("vinubhagavath@ucsb.edu")) {
                    FirebaseDatabase.getInstance().getReference().child("user 1").setValue(new ChatMessage(
                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                            0,
                            puzzle_solved,
                            0,
                            0,
                            1

                    ));
                    //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();
                }

                if (user.equals("vinu021295@gmail.com")) {
                    FirebaseDatabase.getInstance().getReference().child("user 2").setValue(new ChatMessage(
                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                            0,
                            puzzle_solved,
                            0,
                            0,
                            1
                    ));
                    //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();
                }

                alertDialogBuilder.setTitle("Sudoku");
                alertDialogBuilder
                        .setMessage("You have Won")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                if (user.equals("vinubhagavath@ucsb.edu")) {
                                    FirebaseDatabase.getInstance().getReference().child("user 1").setValue(new ChatMessage(
                                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                            0,
                                            0,
                                            0,
                                            0,
                                            1

                                    ));
                                    //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();
                                }

                                if (user.equals("vinu021295@gmail.com")) {
                                    FirebaseDatabase.getInstance().getReference().child("user 2").setValue(new ChatMessage(
                                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                            0,
                                            0,
                                            0,
                                            0,
                                            1
                                    ));
                                    //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();
                                }


                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            }

                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();





            }
            else
            {
                alertDialogBuilder.setTitle("Sudoku");
                alertDialogBuilder
                        .setMessage("Not solved yet!\n\nKEEP TRYING")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }

                        });
            }

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        public int find_index(int id){
            int index=-1;
            for(int i=0;i<81;i++)
            {
                if(ids[i]==id)
                    index=i;
            }
            return index;

        }
        public int count_zeros(int a[][])
        {
            int c=0;
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    if(a[i][j]==0)
                        c++;
                }
            }

            return c;
        }
        public void clickSolve(View view)
        {
            TextView t;
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    t=findViewById(ids[i*9+j]);
                    t.setText(Integer.toString(solved[i][j]));

                }
            }
            pb=findViewById(R.id.progressBar);
            Button b1=findViewById(R.id.button);
            Button b2=findViewById(R.id.button2);
            pb.setVisibility(View.INVISIBLE);
            b1.setVisibility(View.INVISIBLE);
            b2.setVisibility(View.INVISIBLE);

            timer_run=0;
            active=0;

        }
        public int getradiovalue(int a)
        {
            int r=0;

            switch(a)
            {
                case R.id.r1:
                    r=1;
                    break;

                case R.id.r2:
                    r=2;
                    break;

                case R.id.r3:
                    r=3;
                    break;

                case R.id.r4:
                    r=4;
                    break;

                case R.id.r5:
                    r=5;
                    break;

                case R.id.r6:
                    r=6;
                    break;

                case R.id.r7:
                    r=7;
                    break;

                case R.id.r8:
                    r=8;
                    break;

                case R.id.r9:
                    r=9;
                    break;

                case R.id.r10:
                    r=0;
                    break;

            }

            return r;
        }
        public void clickClear(View view)
        {
            TextView t;
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    t=findViewById(ids[i*9+j]);
                    if(puzzle_clear[i][j]==0)
                        t.setText("");
                    else
                        t.setText(Integer.toString(puzzle_clear[i][j]));
                    puzzle[i][j]=puzzle_clear[i][j];

                }
            }

            ProgressBar pb=pb=findViewById(R.id.progressBar);
            pb.setProgress(getprogress());

        }

    private void sendMessage() {

        user=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        TextView user1=findViewById(R.id.u);
        //user1.setText(user);

        if (user.equals("vinubhagavath@ucsb.edu")) {

            TextView user2=findViewById(R.id.u2);
            //user2.setText("vinu021295@gmail.com");

            FirebaseDatabase.getInstance().getReference().child("user 2").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    if(chatMessage!=null) {
                        pb2.setProgress(chatMessage.getProgress());
                        ready2 = (chatMessage.getReady());
                        user_active2 = (chatMessage.getUser_active());
                        puzzle_solved2 = (chatMessage.getPuzzle_solved());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            FirebaseDatabase.getInstance().getReference().child("user 2/user_active").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int chatMessage = Integer.parseInt(dataSnapshot.getValue().toString());
                    //Toast.makeText(getApplicationContext(), Integer.toString(chatMessage),Toast.LENGTH_SHORT).show();


                        if(chatMessage==1){
                            init();

                        }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            FirebaseDatabase.getInstance().getReference().child("user 2/puzzle_solved").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int chatMessage = Integer.parseInt(dataSnapshot.getValue().toString());
                    //Toast.makeText(getApplicationContext(), Integer.toString(chatMessage),Toast.LENGTH_SHORT).show();


                    if(chatMessage==1){

                        alertDialogBuilder.setTitle("Sudoku");
                        alertDialogBuilder
                                .setMessage("Opponent Won")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        FirebaseDatabase.getInstance().getReference().child("user 1").setValue(new ChatMessage(
                                                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                                FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                                0,
                                                0,
                                                0,
                                                0,
                                                puzzle_id

                                        ));
                                        //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();



                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                    }

                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();



                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




        }
        if (user.equals("vinu021295@gmail.com")) {

            TextView user2=findViewById(R.id.u2);
            //user2.setText("vinubhagavath@ucsb.edu");

            FirebaseDatabase.getInstance().getReference().child("user 1").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    if(chatMessage!=null) {
                        pb2.setProgress(chatMessage.getProgress());
                        ready2 = (chatMessage.getReady());
                        user_active2 = (chatMessage.getUser_active());
                        puzzle_solved2 = (chatMessage.getPuzzle_solved());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            FirebaseDatabase.getInstance().getReference().child("user 1/user_active").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int chatMessage = Integer.parseInt(dataSnapshot.getValue().toString());
                    //Toast.makeText(getApplicationContext(), Integer.toString(chatMessage),Toast.LENGTH_SHORT).show();


                    if(chatMessage==1){
                        init();

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            FirebaseDatabase.getInstance().getReference().child("user 1/puzzle_solved").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int chatMessage = Integer.parseInt(dataSnapshot.getValue().toString());
                    //Toast.makeText(getApplicationContext(), Integer.toString(chatMessage),Toast.LENGTH_SHORT).show();


                    if(chatMessage==1){

                        alertDialogBuilder.setTitle("Sudoku");
                        alertDialogBuilder
                                .setMessage("Opponent Won")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        FirebaseDatabase.getInstance().getReference().child("user 2").setValue(new ChatMessage(
                                                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                                FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                                0,
                                                0,
                                                0,
                                                0,
                                                puzzle_id

                                        ));
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                    }

                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();





                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        //Toast.makeText(getApplicationContext(), "displayChatMessage() called",Toast.LENGTH_SHORT).show();

    }
    public void init()
    {
        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
        findViewById(R.id.button).setVisibility(View.VISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        findViewById(R.id.Squares).setVisibility(View.VISIBLE);
        findViewById(R.id.time).setVisibility(View.VISIBLE);
        findViewById(R.id.Radio_numbers).setVisibility(View.VISIBLE);
        findViewById(R.id.gl).setVisibility(View.VISIBLE);
        findViewById(R.id.button2).setVisibility(View.VISIBLE);
        findViewById(R.id.u2).setVisibility(View.VISIBLE);
        findViewById(R.id.u).setVisibility(View.VISIBLE);
        findViewById(R.id.entry_screen).setVisibility(View.VISIBLE);
        findViewById(R.id.entry_screen).setVisibility(View.INVISIBLE);

        Random rand = new Random();
            int r = rand.nextInt(5);
            r=1;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    puzzle[i][j] = puzzle_all[(difficulty - 1) * 5 + r][i][j];
                    puzzle_clear[i][j] = puzzle_all[(difficulty - 1) * 5 + r][i][j];
                    solved[i][j] = solved_all[(difficulty - 1) * 5 + r][i][j];
                }
            }
            String temp = "";

            active = 1;
            zeros = count_zeros(puzzle);

            time = (TextView) findViewById(R.id.time);
            new CountDownTimer(30000000, 1000) {

                public void onTick(long millisUntilFinished) {
                    if (timer_run == 1) {
                        long t = 30000 - (millisUntilFinished / 1000);
                        min = (int) (t / 60);
                        sec = (int) (t - min * 60);
                        time.setText(min + ":" + String.format("%02d", sec));
                    }
                }

                public void onFinish() {
                    time.setText("done!");
                }

            }.start();
            //txtp=findViewById(R.id.prog);

            radio_group = findViewById(R.id.Radio_numbers);

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    tv = findViewById(ids[i * 9 + j]);
                    if (puzzle[i][j] == 0) {
                        tv.setBackgroundColor(Color.rgb(0, 255, 0));
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (active == 1) {
                                    int selected_id = radio_group.getCheckedRadioButtonId();
                                    radio_button = (RadioButton) findViewById(selected_id);
                                    if (radio_button != null) {
                                        t = getradiovalue(radio_button.getId());


                                        if (t == 0) {
                                            txtv = findViewById(view.getId());
                                            txtv.setText("");
                                        } else {
                                            txtv = findViewById(view.getId());
                                            txtv.setText(String.valueOf(t));
                                        }
                                        int index = find_index(view.getId());
                                        puzzle[(int) (index / 9)][index % 9] = t;

                                        pb.setProgress(getprogress());

                                        if (user.equals("vinubhagavath@ucsb.edu")) {
                                            FirebaseDatabase.getInstance().getReference().child("user 1").setValue(new ChatMessage(
                                                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                                    getprogress(),
                                                    puzzle_solved,
                                                    ready,
                                                    user_active,
                                                    puzzle_id

                                            ));
                                            //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();
                                        }

                                        if (user.equals("vinu021295@gmail.com")) {
                                            FirebaseDatabase.getInstance().getReference().child("user 2").setValue(new ChatMessage(
                                                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                                    getprogress(),
                                                    puzzle_solved,
                                                    ready,
                                                    user_active,
                                                    puzzle_id
                                            ));
                                            //Toast.makeText(getApplicationContext(), "Value pushed", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                    //txtp.setText(String.valueOf(getprogress()));
                                }

                            }
                        });


                    } else {
                        tv.setText(Integer.toString(puzzle[i][j]));
                        tv.setBackgroundColor(Color.rgb(255, 0, 0));
                    }
                }
            }

    }

    }


