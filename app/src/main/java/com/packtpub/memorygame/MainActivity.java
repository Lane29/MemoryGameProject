package com.packtpub.memorygame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static int flipTimeMsc = 150;

    public static boolean isTimeTrialGame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Start game 1 Level 1 with time limit
    public void onButtonTimeTrialClick(View view) {
        isTimeTrialGame = true;
        Intent i;
        i = new Intent(this, Game1Level1Activity.class);
        startActivity(i);
    }

    //Start game 1 Level 1
    public void onButtonAdventureClick(View view) {
        isTimeTrialGame = false;
        Intent i;
        i = new Intent(this, Game1Level1Activity.class);
        startActivity(i);
    }

    //Show the table of results
    public void onResultsClick(View view) {
        Intent i;
        i = new Intent(this, ResultsActivity.class);
        startActivity(i);
    }

    //Show the page with rules
    public void onRulesClick(View view) {
        Intent i;
        i = new Intent(this, RulesActivity.class);
        startActivity(i);
    }
}
