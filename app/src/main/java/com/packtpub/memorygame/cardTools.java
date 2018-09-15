package com.packtpub.memorygame;

import android.util.Log;

import java.util.Random;

public class cardTools {

    public static int[] initPackArray(int lengthOfPack, int numberOfCardsInSet){
        // Log.i("info", "initPackArray...............");
        int[] pack = new int[lengthOfPack];
        for (int i = 0; i < lengthOfPack; i++) {    //Initialising pack array. For level #1 = [1;2;3;4;5;6;1;2;3;4;5;6]
            pack[i] = (i + 1) % numberOfCardsInSet + 1;
            //Log.i("info", "initPackArray...............pack["+i+"]="+pack[i]);
        }
        return pack;
    }

    public static boolean[] initPlayedCardsArray(int lengthOfPack) {
        // Log.i("info", "initPlayedCardsArray...............");
        boolean[] playedCards = new boolean[lengthOfPack];
        for (int i = 0; i < lengthOfPack; i++) {
            playedCards[i] = false;
        }
        return playedCards;
    }

    public static int[] initOpenedCardsValuesArray(int numOfMatchedCards){
        /// Log.i("info", "initOpenedCardsValuesArray...............");
        int[] openedCardsValues = new int[numOfMatchedCards];
        for (int i = 0; i < numOfMatchedCards; i++) {
            openedCardsValues[i] = 0;
        }
        return openedCardsValues;
    }

    public static int[] initOpenedCardsPositionsArray(int numOfMatchedCards) {
        /// Log.i("info", "initOpenedCardsPositionsArray...............");
        int[] openedCardsPositions = new int[numOfMatchedCards];
        for (int i = 0; i < numOfMatchedCards; i++) {
            openedCardsPositions[i] = 0;
        }
        return openedCardsPositions;
    }

    public static int[] shuffleCards(int[] pack) {
        // Log.i("info", "shuffleCards...............");
        Random random = new Random();

        for (int i = 0; i < pack.length; i++) {
            int cardPosition = random.nextInt(pack.length - 1);
            int curCard = pack[i];
            pack[i] = pack[cardPosition];
            pack[cardPosition] = curCard;
        }
        return pack;
    }

    public static boolean areAllCardsPlayed(boolean[] playedCards, int lengthOfPack) {
        boolean res = true;
        int i=0;
        while (i < lengthOfPack && res) {
            if (!playedCards[i]) res = false;
            i++;
        }
        return res;
    }

    public static boolean[] addPlayedCards(int[] openedCardsPositions, boolean[] playedCards, int numOfMatchedCards) {
        for (int i = 0; i < numOfMatchedCards; i++) {
            int position = openedCardsPositions[i];
            playedCards[position-1] = true;
        }
        return playedCards;
    }

    public static String getCardIDByPosition(int openedCardsPosition, int numCols) {
        //Log.i("info", "........getCardIDByPosition...openedCardsPosition = " + openedCardsPosition);
        int cardCol = openedCardsPosition % numCols;
        int cardRow = openedCardsPosition / numCols;
        if (cardCol == 0) {
            cardCol = numCols;
        }
        else {
            cardRow++;
        }
        String cardId = "card" + cardRow + cardCol;
        //Log.i("info", "........getCardIDByPosition...cardId = " + cardId);
        return cardId;
    }

    public static int getCardRowByPosition(int openedCardsPosition, int numCols) {
        Log.i("info", "........getCardRowByPosition...openedCardsPosition = " + openedCardsPosition);
        int cardCol = openedCardsPosition % numCols;
        int cardRow = openedCardsPosition / numCols;
        if (cardCol != 0) cardRow++;
        Log.i("info", "........getCardRowByPosition...cardRow = " + cardRow);
        return cardRow;
    }

    public static int getCardColByPosition(int openedCardsPosition, int numCols) {
        Log.i("info", "........getCardColByPosition...openedCardsPosition = " + openedCardsPosition);
        int cardCol = openedCardsPosition % numCols;
        if (cardCol == 0) cardCol = numCols;
        Log.i("info", "........getCardRowByPosition...cardCol = " + cardCol);
        return cardCol;
    }

    public static boolean neededNumberOfCardsIsOpened(int[] openedCardsValues, int numOfMatchedCards) {
        Log.i("info", "neededNumberOfCardsIsOpened...............Yes!!!!");
        boolean res = true;
        int i = 0;
        while (i<numOfMatchedCards && res) {
            if (openedCardsValues[i] == 0) res = false;
            i++;
        }
        return res;
    }

    public static boolean areOpenedCardsMatch(int[] openedCardsValues, int numOfMatchedCards) {
        //Log.i("info", "areOpenedCardsMatch...............");
        boolean res = true;
        int i = 1;
        while (i < numOfMatchedCards && res) {
            if (openedCardsValues[i] != openedCardsValues[i-1]) res = false;
            //Log.i("info", "openedCardsValues[" + i + "] = "+ openedCardsValues[i] + "    i = " + i + "   match previous = " + res);
            i++;
        }
        return res;
    }

    public static void addOpenedCard(int[] pack, int positionInPack, int[] openedCardsValues, int[] openedCardsPositions, int numOfMatchedCards) {
        Log.i("info", "addOpenedCard...............numOfMatchedCards = " + numOfMatchedCards);
        int i = 0;
        while (i < numOfMatchedCards   &&   openedCardsValues[i] != 0) {
            Log.i("info", "addOpenedCard...............openedCardsValues["+i+"] = " + openedCardsValues[i]);
            i++;
        }
        openedCardsValues[i] = pack[positionInPack-1];
        openedCardsPositions[i] = positionInPack;
    }

    public static String getTextTime(long time){
        int intTime = (int) time / 1000;
        int minutes = (int) intTime / 60;
        int seconds = (int) intTime % 60;
        String addMinutes = "";
        String addSeconds = "";
        if (minutes < 10) addMinutes = "0";
        if (seconds < 10) addSeconds = "0";
        return addMinutes + minutes + ":" + addSeconds + seconds;
    }

    public static boolean isClickedCardAlreadyOpened(int[] openedCardsPositions, int positionInPack) {
        boolean res = true;
        int i = 0;
        Log.i("info", "isClickedCardAlreadyOpened...............positionInPack = " + positionInPack);
        while (i < openedCardsPositions.length   &&   openedCardsPositions[i] != positionInPack) {
            i++;
            res = false;
        }
        Log.i("info", "isClickedCardAlreadyOpened...............res = " + res);

        return res;
    }

    public static int strTimeToSec(String bestTime) {
        //strTime looks like "13:47"
        int res = 0;
        String minutes = bestTime.substring(0,2);
        //Log.i("info", "strTimeToMsc.........minutes = " + minutes);
        String seconds = bestTime.substring(3);
        //Log.i("info", "strTimeToMsc.........seconds = " + seconds);
        res = Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds);
        //Log.i("info", "strTimeToMsc.........res = " + res);
        return res;
    }
}
