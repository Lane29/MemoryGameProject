package com.packtpub.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        TextView textView = (TextView) findViewById(R.id.textViewRules);
        textView.setText(Html.fromHtml("<p>Object of the game: " +
                "remove all cards by opening<br>- two (in Level 1)<br>" +
                "- three (in Level 2)<br>" +
                "- three (in Level 3)<br>" +
                "matched cards.</p>" +
                "<p>After shuffling cards are laid face down, in rows.</p>" +
                "</p>Turn over any two (in Level 1) or three (in Level 2 and Level 3) cards (one at a time)." +
                "If they match, the cards are removed from the table. " +
                "If they do not match, the cards are turned face down.</p>" +
                "<p>To win a game quicker, it is recommended to remember the values of opened cards." +
                "<p></p>Best times are saved in the table of results for each level.</p>"));
    }

    public void onBackClick(View view) {
        Intent i;
        i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
