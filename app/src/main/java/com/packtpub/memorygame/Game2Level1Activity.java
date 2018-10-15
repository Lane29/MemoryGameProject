package com.packtpub.memorygame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Game2Level1Activity extends AppCompatActivity implements View.OnClickListener{

    private int numOfMatchedCards = 0;

    private int numRows = 0;
    private int numCols = 0;

    private int lengthOfPack = 0;
    private int numberOfCardsInSet = 0;

    private int pack[];
    private boolean playedCards[];//Flags for cards that are already removed

    private int openedCardsValues[];
    private int openedCardsPositions[];

    private int cntMoves = 0;

    private MediaPlayer flipMediaPlayer;
    private MediaPlayer matchMediaPlayer;
    private MediaPlayer winMediaPlayer;

    boolean isPlayStarted = false;
    boolean isOpenedCardsValuesArrayInitiated = true;

    //True - if a card is turned, False - if a card is on its edge
    boolean isCardOnItsEdge;
    boolean isCardFaceDown;

    ImageView cardToFlip;

    TextView textTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2_level1);

        numOfMatchedCards = 2;
        numRows = 3;
        numCols = 4;

        textTime = (TextView) findViewById(R.id.textTime);
        textTime.setText("Time: 00:00");

        flipMediaPlayer = MediaPlayer.create(this, R.raw.memory_flip);
        matchMediaPlayer = MediaPlayer.create(this, R.raw.memory_match);
        winMediaPlayer = MediaPlayer.create(this, R.raw.memory_fanfare);
Log.i("info", "Game2Level1....onCreate...before initGame");
        initGame();
        Log.i("info", "Game2Level1....onCreate...before shuffleCards");

        cardTools.shuffleCards(pack);
        Log.i("info", "Game2Level1....onCreate...before showAllCardsFaceUp");

        showAllCardsFaceUp();
        Log.i("info", "Game2Level1....onCreate...before Handler");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                flipALLCards();
            }
        }, 2000);
        Log.i("info", "Game2Level1....onCreate...end");
    }

    private void initGame(){
        lengthOfPack = numRows * numCols;           //For level #1 = 12 cards are laid
        numberOfCardsInSet = lengthOfPack / numOfMatchedCards;    //For level #1 = 6 cards in a set

        //Array for all cards
        pack = cardTools.initPackArray(lengthOfPack, numberOfCardsInSet);

        //Array for flags for played cards
        playedCards = cardTools.initPlayedCardsArray(lengthOfPack);;

        //Array for opened cards
        openedCardsValues = cardTools.initOpenedCardsValuesArray(numOfMatchedCards);
        isOpenedCardsValuesArrayInitiated = true;

        //Array for positions of opened cards
        openedCardsPositions = cardTools.initOpenedCardsPositionsArray(numOfMatchedCards);

        initTextMoves();
        initTextInstructions(false);

        setOnClickListenerOnImageViews();
        isPlayStarted = false;

        Button buttonStart = findViewById(R.id.button);
        buttonStart.setTag(0);
    }

    private void initTextMoves() {
        cntMoves = 0;
        TextView textMoves = findViewById(R.id.textMoves);
        String str = "Moves: 0";
        textMoves.setText(str);
    }

    private void initTextInstructions(boolean deleteText) {
        TextView textInstructions = findViewById(R.id.textViewInstructions);
        String str = "Find Two Match";
        if (deleteText) str = "";
        textInstructions.setText(str);
    }

    private void setOnClickListenerOnImageViews() {
        //Setting onClickListener on each imageView
        ImageView imageView;
        for (int i = 1; i <= numRows; i++)
            for (int j = 1; j <= numCols; j++) {
                int resID = getResources().getIdentifier("card" + i + j, "id", getPackageName());
                imageView = findViewById(resID);
                imageView.setOnClickListener(this);
            }
    }

    private void removeOnClickListenerOnImageViews() {
        //remove onClickListener from each imageView
        ImageView imageView;
        for (int i = 1; i <= numRows; i++)
            for (int j = 1; j <= numCols; j++) {
                int resID = getResources().getIdentifier("card" + i + j, "id", getPackageName());
                imageView = findViewById(resID);
                imageView.setOnClickListener(null);
            }
    }

    public void onClick(View view) {
        //Start timer when user starts playing
        if (!isPlayStarted){
            setTimer();
            isPlayStarted = true;
        }

        ImageView clickedCard = (ImageView) view;

        //Position of the clicked card in a whole pack
        int positionInPack = Integer.parseInt(clickedCard.getTag().toString());

        //Check whether user tried to open the card while needed numer of cards are already opened
        //Or user clicked on the already opened card
        if (!cardTools.neededNumberOfCardsIsOpened(openedCardsValues, numOfMatchedCards)&&
                !cardTools.isClickedCardAlreadyOpened(openedCardsPositions, positionInPack)) {
            isCardFaceDown = true;
            isCardOnItsEdge = false;

            flipCard(clickedCard);

            cntMoves++;
            showMoves();
            //add position of the opened card to the array openedCards
            cardTools.addOpenedCard(pack, positionInPack, openedCardsValues, openedCardsPositions, numOfMatchedCards);

            if (cardTools.neededNumberOfCardsIsOpened(openedCardsValues, numOfMatchedCards)) {
              if (cardTools.areOpenedCardsMatch(openedCardsValues, numOfMatchedCards)) {
                  Handler handler = new Handler();
                  handler.postDelayed(new Runnable() {
                      public void run() {
                          matchMediaPlayer.start();
                          removeMatchedCards();

                          cardTools.addPlayedCards(openedCardsPositions, playedCards, numOfMatchedCards);

                          openedCardsValues = cardTools.initOpenedCardsValuesArray(numOfMatchedCards);
                          openedCardsPositions = cardTools.initOpenedCardsPositionsArray(numOfMatchedCards);

                          if (cardTools.areAllCardsPlayed(playedCards, lengthOfPack)) {
                              winMediaPlayer.start();
                              isPlayStarted = false;
                              removeOnClickListenerOnImageViews();
                              Button buttonStart = findViewById(R.id.button);
                              buttonStart.setText("Next level");
                              buttonStart.setTag(1); //1 means go to next level instead of re-start the game when tag=0
                              initTextInstructions(true);
                              saveResult();
                          }
                      }
                  }, MainActivity.flipTimeMsc*4);
              } //Opened cards don't match, wait flipTimeMsc*4 and close cards automatically
                else {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            flipOpenedCards();
                        }
                    }, MainActivity.flipTimeMsc*4);
                }
            }
        }
    }

    private void saveResult() {

        String level1BestTime = "level1BestTime";
        String defaultTime = "59:59";

        SharedPreferences prefs = getSharedPreferences(level1BestTime, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        //Load existing Best Time or if it is not available default (0)
        String bestTime = prefs.getString(level1BestTime, defaultTime);
        String curTime = textTime.getText().toString().substring(6);

        int bestTimeInSec = cardTools.strTimeToSec(bestTime);
        int curTimeInSec = cardTools.strTimeToSec(curTime);

        Toast toast;
        if (curTimeInSec < bestTimeInSec) {
            editor = prefs.edit();
            editor.putString(level1BestTime, curTime);
            editor.commit();
            toast = Toast.makeText(getApplicationContext(), "CONGRATULATIONS! Best Time!", Toast.LENGTH_LONG);
        }
        else toast = Toast.makeText(getApplicationContext(), "CONGRATULATIONS!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

  /*  //Re-start the game
    public void onStartClick(View view) {
        if (view.getTag().toString().equals("0")) {
            cardTools.shuffleCards(pack);
            moveBackAllCards();
            showAllCardsFaceDown();
            playedCards = cardTools.initPlayedCardsArray(lengthOfPack);
            openedCardsValues = cardTools.initOpenedCardsValuesArray(numOfMatchedCards);
            initTextMoves();
            setOnClickListenerOnImageViews();
            textTime.setText("Time: 00:00");
            isPlayStarted = false;
        }
        else {
            Intent i;
            i = new Intent(this, Game2Level2Activity.class);
            startActivity(i);
        }
    }*/

    private void removeMatchedCards() {
        ImageView imageView;
        for (int i = 0; i < numOfMatchedCards; i++) {
            int resID = getResources().getIdentifier(cardTools.getCardIDByPosition(openedCardsPositions[i], numCols), "id", getPackageName());
            imageView = findViewById(resID);
            imageView.animate().translationXBy(-2000f).setDuration(2000);
        }
    }

    private void moveBackAllCards() {
        ImageView imageView;
        int positionToMoveBack, i, j;
        for (int n = 0; n < playedCards.length; n++) {
            if (playedCards[n]) {
                positionToMoveBack = n + 1;
                i = cardTools.getCardRowByPosition(positionToMoveBack, numCols);
                j = cardTools.getCardColByPosition(positionToMoveBack, numCols);
                int resID = getResources().getIdentifier("card" + i + j, "id", getPackageName());
                imageView = findViewById(resID);
                imageView.animate().translationXBy(2000f).setDuration(0);
            }
        }
    }

    private void showMoves() {
        TextView textMoves = findViewById(R.id.textMoves);
        String strMoves = "Moves: " + cntMoves;
        textMoves.setText(strMoves);
    }

    //Turn all cards face down
    private void showAllCardsFaceDown() {
        for (int i = 1; i <= numRows; i++)
            for (int j = 1; j <= numCols; j++)  showCardFaceDown(i, j);
    }

    private void showCardFaceDown(int i, int j) {
        int resID = getResources().getIdentifier("card" + i + j, "id", getPackageName());
        ImageView imageView = findViewById(resID);
        int id = getResources().getIdentifier("bw", "drawable", getPackageName());
        imageView.setImageResource(id);
    }

    //Turn only opened cards face down
    private void flipOpenedCards() {
        isCardFaceDown = false;
        isCardOnItsEdge = false;

        CountDownTimer timer = new CountDownTimer(MainActivity.flipTimeMsc*4, MainActivity.flipTimeMsc*2) {
            @Override
            public void onTick(long l) {
                float scaleXValue = 1f; //Default value - for turning fully

                //if the card flipped than we need to put it on the edge
                if (!isCardOnItsEdge) scaleXValue = 0.01f;

                for (int n = 0; n < openedCardsPositions.length; n++) {
                    int i = cardTools.getCardRowByPosition(openedCardsPositions[n], numCols);
                    int j = cardTools.getCardColByPosition(openedCardsPositions[n], numCols);
                    int resID = getResources().getIdentifier("card" + i + j, "id", getPackageName());

                    cardToFlip = findViewById(resID);
                    //if the card on its edge - it's the time to change image and then turn it
                    if (isCardOnItsEdge) cardToFlip.setImageResource(R.drawable.bw);

                    //Make sound
                    flipMediaPlayer.start();
                    cardToFlip.animate().scaleX(scaleXValue).setDuration(MainActivity.flipTimeMsc);
                }
                if (!isCardOnItsEdge) isCardOnItsEdge = true;
            }

            @Override
            public void onFinish() {
                openedCardsValues = cardTools.initOpenedCardsValuesArray(numOfMatchedCards);
                openedCardsPositions = cardTools.initOpenedCardsPositionsArray(numOfMatchedCards);
            }
        };
        timer.start();
    }

    private void flipCard(ImageView card){
        cardToFlip = card;
        CountDownTimer timer = new CountDownTimer(MainActivity.flipTimeMsc*4, MainActivity.flipTimeMsc*2) {
            @Override
            public void onTick(long l) {
                float scaleXValue = 1f; //Default value - for turning to flat position

                //if the card already flipped than it will be turned on to its edge
                if (!isCardOnItsEdge) {
                    isCardOnItsEdge = true;
                    scaleXValue = 0.01f;
                } else {//if the card on its edge - it's the time to change image and then turn it
                    if (isCardFaceDown) {
                        //Turn the card face up (name of the target resource looks like "image<X>")
                        int positionInPack = Integer.parseInt(cardToFlip.getTag().toString());
                        int id = getResources().getIdentifier("image" + pack[positionInPack - 1], "drawable", getPackageName());
                        cardToFlip.setImageResource(id);
                        isCardFaceDown = false;
                    } else {
                        cardToFlip.setImageResource(R.drawable.bw);
                        isCardFaceDown = true;
                    }
                }
                //Make sound
                flipMediaPlayer.start();
                cardToFlip.animate().scaleX(scaleXValue).setDuration(MainActivity.flipTimeMsc);
            }

            @Override
            public void onFinish() {
            }
        };
        timer.start();
    }

    private void setTimer() {
        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            private long time = 0;
            @Override
            public void run()
            {
                time += 1000;
                if (isPlayStarted) {
                    textTime.setText("Time: " + cardTools.getTextTime(time));
                    h.postDelayed(this, 1000);
                }
                else h.removeCallbacks(this);
            }
        }, 1000); // 1 second
    }

    //Turn all cards face up
    private void showAllCardsFaceUp() {
        for (int i = 1; i <= numRows; i++)
            for (int j = 1; j <= numCols; j++)  showCardFaceUp(i, j);
    }


    //Re-start the game
    public void onStartClick(View view) {
        if (view.getTag().toString().equals("0")) {
            cardTools.shuffleCards(pack);
            moveBackAllCards();

            //===================================================== trial

            showAllCardsFaceUp();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    flipALLCards();
                }
            }, 3000);
//===================================================== trial

            //showAllCardsFaceDown();
//===================================================== trial



            playedCards = cardTools.initPlayedCardsArray(lengthOfPack);
            openedCardsValues = cardTools.initOpenedCardsValuesArray(numOfMatchedCards);
            initTextMoves();
            setOnClickListenerOnImageViews();
            textTime.setText("Time: 00:00");
            isPlayStarted = false;
        }
        else {
            Intent i;
            i = new Intent(this, Game1Level2Activity.class);
            startActivity(i);
        }
    }

    private void showCardFaceUp(int i, int j) {
        int resID = getResources().getIdentifier("card" + i + j, "id", getPackageName());
        ImageView imageView = findViewById(resID);
        int positionInPack = Integer.parseInt(imageView.getTag().toString());
        int id = getResources().getIdentifier("image" + pack[positionInPack - 1], "drawable", getPackageName());
        imageView.setImageResource(id);
    }


    //Turn only opened cards face down
    private void flipALLCards() {
        isCardFaceDown = false;
        isCardOnItsEdge = false;

        CountDownTimer timer = new CountDownTimer(MainActivity.flipTimeMsc*4, MainActivity.flipTimeMsc*2) {
            @Override
            public void onTick(long l) {
                float scaleXValue = 1f; //Default value - for turning fully

                //if the card flipped than we need to put it on the edge
                if (!isCardOnItsEdge) scaleXValue = 0.01f;

                for (int n = 0; n < pack.length; n++) {

                    int i = cardTools.getCardRowByPosition(n+1, numCols);
                    int j = cardTools.getCardColByPosition(n+1, numCols);
                    int resID = getResources().getIdentifier("card" + i + j, "id", getPackageName());
                    Log.i("info", "flipALLCards...card" + i + j);
                    cardToFlip = findViewById(resID);
                    //if the card on its edge - it's the time to change image and then turn it
                    if (isCardOnItsEdge) cardToFlip.setImageResource(R.drawable.bw);

                    //Make sound
                    flipMediaPlayer.start();
                    cardToFlip.animate().scaleX(scaleXValue).setDuration(MainActivity.flipTimeMsc);
                }
                if (!isCardOnItsEdge) isCardOnItsEdge = true;
            }

            @Override
            public void onFinish() {
                //
            }
        };
        timer.start();
    }
}