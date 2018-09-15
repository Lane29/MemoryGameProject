package com.packtpub.memorygame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String level1BestTime = "level1BestTime";
        String level2BestTime = "level2BestTime";
        String level3BestTime = "level3BestTime";
        String defaultTime = "0";
        String bestTime1;
        String bestTime2;
        String bestTime3;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        SharedPreferences prefs = getSharedPreferences(level1BestTime, MODE_PRIVATE);
        //Load existing Best Time for the level or if it is not available default (0)
        bestTime1 = prefs.getString(level1BestTime, defaultTime);

        prefs = getSharedPreferences(level2BestTime, MODE_PRIVATE);
        bestTime2 = prefs.getString(level2BestTime, defaultTime);

        prefs = getSharedPreferences(level3BestTime, MODE_PRIVATE);
        bestTime3 = prefs.getString(level3BestTime, defaultTime);

        TextView textViewLevel1 = (TextView) findViewById(R.id.textViewResLevel1);
        TextView textViewLevel2 = (TextView) findViewById(R.id.textViewResLevel2);
        TextView textViewLevel3 = (TextView) findViewById(R.id.textViewResLevel3);

        if (bestTime1.equals("0"))textViewLevel1.setText("-");
        else textViewLevel1.setText(bestTime1);

        if (bestTime2.equals("0")) textViewLevel2.setText("-");
        else textViewLevel2.setText(bestTime2);

        if (bestTime3.equals("0")) textViewLevel3.setText("-");
        else textViewLevel3.setText(bestTime3);
    }

    public void onBackClick(View view) {
        Intent i;
        i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
