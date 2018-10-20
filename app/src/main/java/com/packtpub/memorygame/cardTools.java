package com.packtpub.memorygame;

import android.util.Log;
import android.widget.ImageView;

import java.util.Random;

public class cardTools {

    private static final int MULTIPLIER_LOW = 2;
    private static final int MULTIPLIER_AVERAGE = 3;
    private static final int MULTIPLIER_HIGH = 4;

    public static int[] initPackArray(int lengthOfPack, int numberOfCardsInSet){
        int[] pack = new int[lengthOfPack];
        for (int i = 0; i < lengthOfPack; i++) {    //Initialising pack array. For level #1 = [1;2;3;4;5;6;1;2;3;4;5;6]
            pack[i] = (i + 1) % numberOfCardsInSet + 1;
        }
        return pack;
    }

    public static boolean[] initPlayedCardsArray(int lengthOfPack) {
        boolean[] playedCards = new boolean[lengthOfPack];
        for (int i = 0; i < lengthOfPack; i++) {
            playedCards[i] = false;
        }
        return playedCards;
    }

    public static int[] initOpenedCardsValuesArray(int numOfMatchedCards){
        int[] openedCardsValues = new int[numOfMatchedCards];
        for (int i = 0; i < numOfMatchedCards; i++) {
            openedCardsValues[i] = 0;
        }
        return openedCardsValues;
    }

    public static int[] initOpenedCardsPositionsArray(int numOfMatchedCards) {
        int[] openedCardsPositions = new int[numOfMatchedCards];
        for (int i = 0; i < numOfMatchedCards; i++) {
            openedCardsPositions[i] = 0;
        }
        return openedCardsPositions;
    }

    public static int[] initPointOfCardsArray(int lengthOfPack) {
        int[] pointOfCardsArray = new int[lengthOfPack];
        for (int i = 0; i < lengthOfPack; i++) {
            pointOfCardsArray[i] = 10;
        }
        return pointOfCardsArray;
    }

    public static int[] shuffleCards(int[] pack) {
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
        int cardCol = openedCardsPosition % numCols;
        int cardRow = openedCardsPosition / numCols;
        if (cardCol == 0) cardCol = numCols;
        else cardRow++;
        String cardId = "card" + cardRow + cardCol;
        return cardId;
    }

    public static int getCardRowByPosition(int openedCardsPosition, int numCols) {
        int cardCol = openedCardsPosition % numCols;
        int cardRow = openedCardsPosition / numCols;
        if (cardCol != 0) cardRow++;
        return cardRow;
    }

    public static int getCardColByPosition(int openedCardsPosition, int numCols) {
        int cardCol = openedCardsPosition % numCols;
        if (cardCol == 0) cardCol = numCols;
        return cardCol;
    }

    public static boolean neededNumberOfCardsIsOpened(int[] openedCardsValues, int numOfMatchedCards) {
        boolean res = true;
        int i = 0;
        while (i<numOfMatchedCards && res) {
            if (openedCardsValues[i] == 0) res = false;
            i++;
        }
        return res;
    }

    public static boolean areOpenedCardsMatch(int[] openedCardsValues, int numOfMatchedCards) {
        boolean res = true;
        int i = 1;
        while (i < numOfMatchedCards && res) {
            if (openedCardsValues[i] != openedCardsValues[i-1]) res = false;
            i++;
        }
        return res;
    }

    public static void addOpenedCard(int[] pack, int positionInPack, int[] openedCardsValues, int[] openedCardsPositions, int numOfMatchedCards) {
        int i = 0;
        while (i < numOfMatchedCards   &&   openedCardsValues[i] != 0) i++;
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
        while (i < openedCardsPositions.length   &&   openedCardsPositions[i] != positionInPack) {
            i++;
            res = false;
        }
        return res;
    }

    public static int strTimeToSec(String bestTime) {
        //strTime looks like "13:47"
        int res = 0;
        String minutes = bestTime.substring(0,2);
        String seconds = bestTime.substring(3);
        res = Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds);
        return res;
    }

    public static int getMultplier(long t1, long t2) {
        int res = 1;
        long difMillis = (t2 - t1)/1000;
        if (difMillis<3) res = MULTIPLIER_HIGH;
        else if (difMillis<4) res = MULTIPLIER_AVERAGE;
        else if (difMillis<5) res = MULTIPLIER_LOW;
        return res;
    }

    public static int countScore(int numOfMatchedCards, int[] pointsOfCards, int[] openedCardsPositions) {
        int res = 0;
        for (int i = 0; i < numOfMatchedCards; i++) {
            res += pointsOfCards[openedCardsPositions[i]-1];
        }
        return res;
    }

}
