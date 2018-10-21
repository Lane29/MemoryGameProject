package com.packtpub.memorygame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String game1Level1BestScore = "game1Level1BestScore";
        String game1Level2BestScore = "game1Level2BestScore";
        String game1Level3BestScore = "game1Level3BestScore";
        String game2Level1BestScore = "game2Level1BestScore";
        String game2Level2BestScore = "game2Level2BestScore";
        String game2Level3BestScore = "game2Level3BestScore";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        SharedPreferences prefs = getSharedPreferences(game1Level1BestScore, MODE_PRIVATE);
        //Load existing Best Score for the level or if it is not available default (0)
        int bestScore11 = prefs.getInt(game1Level1BestScore, 0);

        prefs = getSharedPreferences(game1Level2BestScore, MODE_PRIVATE);
        int bestScore12 = prefs.getInt(game1Level2BestScore, 0);

        prefs = getSharedPreferences(game1Level3BestScore, MODE_PRIVATE);
        int bestScore13 = prefs.getInt(game1Level3BestScore, 0);

        prefs = getSharedPreferences(game2Level1BestScore, MODE_PRIVATE);
        int bestScore21 = prefs.getInt(game2Level1BestScore, 0);

        prefs = getSharedPreferences(game2Level2BestScore, MODE_PRIVATE);
        int bestScore22 = prefs.getInt(game2Level2BestScore, 0);

        prefs = getSharedPreferences(game2Level3BestScore, MODE_PRIVATE);
        int bestScore23 = prefs.getInt(game2Level3BestScore, 0);
        
        
        TextView textViewGame1Level1 = (TextView) findViewById(R.id.textViewResGame1Level1);
        TextView textViewGame1Level2 = (TextView) findViewById(R.id.textViewResGame1Level2);
        TextView textViewGame1Level3 = (TextView) findViewById(R.id.textViewResGame1Level3);
        TextView textViewGame2Level1 = (TextView) findViewById(R.id.textViewResGame2Level1);
        TextView textViewGame2Level2 = (TextView) findViewById(R.id.textViewResGame2Level2);
        TextView textViewGame2Level3 = (TextView) findViewById(R.id.textViewResGame2Level3);

        textViewGame1Level1.setText(Integer.toString(bestScore11));
        textViewGame1Level2.setText(Integer.toString(bestScore12));
        textViewGame1Level3.setText(Integer.toString(bestScore13));
        textViewGame2Level1.setText(Integer.toString(bestScore21));
        textViewGame2Level2.setText(Integer.toString(bestScore22));
        textViewGame2Level3.setText(Integer.toString(bestScore23));
    }

    public void onMenuClick(View view) {
        Intent i;
        i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
