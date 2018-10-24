package com.packtpub.memorygame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static int flipTimeMsc = 150;

    public static boolean isTimeTrialGame;
    public static final int SECONDS_GAME1_LEVEL1 = 60;
    public static final int SECONDS_GAME2_LEVEL1 = 50; //the initial time is lower because all cards are shown
    public static final int SECONDS_INCREMENT_BETWEEN_LEVELS = 5;
    public static int bonusTime = 0;

    public static boolean isMuteClicked= false;

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
