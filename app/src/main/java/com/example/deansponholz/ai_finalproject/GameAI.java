package com.example.deansponholz.ai_finalproject;

import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created by deansponholz on 4/8/17.
 */

public class GameAI {

    //Local GameEnvironment instance
    private GameEnvironment gameEnvironment;

    //variable for the aiMoveLocation
    private int finalAIMove = -1;

    //Depth max for Minimax
    public static int depthMax;


    //Constructor - creates instance of GameAI
    public GameAI(GameEnvironment gameEnvironment){

        this.gameEnvironment = gameEnvironment;

    }


    //Investigate a game state and see whether it terminal
    //Returns Either 1, 2, 0
    public int evaluateState(GameEnvironment gameEnvironment){

        int humanCount = 0;
        int aiCount = 0;

        for(int i=5; i>=0; i--){

            for(int j=0; j<=6; j++){

                if(gameEnvironment.gameGrid[i][j] == 0){
                    continue;
                }


                //Loop through all cells right of current location
                if(j<=3){

                    for(int k=0; k<4; k++){

                        if(gameEnvironment.gameGrid[i][j+k] == 1){
                            aiCount++;
                        }
                        else if(gameEnvironment.gameGrid[i][j+k] == 2){
                            humanCount++;
                        }
                        else{
                            break;
                        }
                    }

                    if(aiCount == 4){
                        //Log.d("evaluateState", "AI");
                        //Ai win
                        return 1;
                    }
                    else if (humanCount == 4){
                        //Log.d("evaluateState", "HUMAN");
                        //Human Win
                        return 2;
                    }

                    aiCount = 0;
                    humanCount = 0;
                }


                //Loop through all cells above of current location
                if(i>=3){

                    for(int k=0; k<4; k++){

                        if(gameEnvironment.gameGrid[i-k][j] == 1){
                            aiCount++;
                        }
                        else if(gameEnvironment.gameGrid[i-k][j] == 2){
                            humanCount++;
                        }
                        else{
                            break;
                        }
                    }
                    if(aiCount == 4){
                        //Log.d("evaluateState", "AI");
                        //human win
                        return 1;
                    }
                    else if (humanCount == 4){
                        //Log.d("evaluateState", "HUMAN");
                        //ai win
                        return 2;
                    }

                    aiCount = 0;
                    humanCount = 0;
                }

                //Loop through all cells diagonally up-right of current location
                if(j<=3 && i>= 3){

                    for(int k=0; k<4; k++){

                        if(gameEnvironment.gameGrid[i-k][j+k] == 1){
                            aiCount++;
                        }
                        else if(gameEnvironment.gameGrid[i-k][j+k] == 2){
                            humanCount++;
                        }
                        else{
                            break;
                        }
                    }

                    if(aiCount == 4){
                        //Log.d("evaluateState", "AI");
                        return 1;
                    }

                    else if (humanCount == 4){
                        //Log.d("evaluateState", "HUMAN");
                        return 2;
                    }
                    aiCount = 0;
                    humanCount = 0;
                }

                //Loop through all cells diagonally up-left of current location
                if(j>=3 && i>=3){

                    for(int k=0; k<4; k++){

                        if(gameEnvironment.gameGrid[i-k][j-k] == 1){
                            aiCount++;
                        }
                        else if(gameEnvironment.gameGrid[i-k][j-k] == 2){
                            humanCount++;
                        }
                        else break;
                    }
                    if(aiCount == 4){
                        //Log.d("evaluateState", "AI");
                        return 1;
                    }
                    else if (humanCount == 4){
                        //Log.d("evaluateState", "HUMAN");
                        return 2;
                    }
                    aiCount = 0;
                    humanCount = 0;
                }
            }
        }

        for(int j = 0; j<7; j++){

            //state is not terminal
            if(gameEnvironment.gameGrid[0][j] == 0){
                return -1;

            }
        }


        //tie game
        return 0;
    }

    public int determineScore(int aiScore, int moreMoves){

        int scoreForState = 4 - moreMoves;

        if(aiScore == 0){
            return 0;
        }

        else if(aiScore == 1){
            return scoreForState;
        }

        else if(aiScore == 2){
            return 10*scoreForState;
        }

        else if(aiScore == 3){
            return 100*scoreForState;
        }

        else{
            return 1000;
        }
    }

    //Evaluate board favorableness for AI
    public int heuristicFunction(GameEnvironment gameEnvironment) {

        int blankCount = 0;
        int stateScore = 0;
        int movesLeft = 0;
        int aiInARow = 1;
        int k = 0;


        for (int i = 5; i >= 0; i--) {

            for (int j = 0; j <= 6; j++) {

                if (gameEnvironment.gameGrid[i][j] == 0 || gameEnvironment.gameGrid[i][j] == 2){
                    continue;
                }

                if (j <= 3) {
                    for (k = 1; k < 4; k++) {
                        if (gameEnvironment.gameGrid[i][j + k] == 1){
                            aiInARow++;

                        }
                        else if (gameEnvironment.gameGrid[i][j + k] == 2) {

                            aiInARow = 0;
                            blankCount = 0;
                            break;

                        } else {
                            blankCount++;
                        }
                    }

                    movesLeft = 0;
                    if (blankCount > 0)
                        for (int c = 1; c < 4; c++) {
                            int column = j + c;
                            for (int m = i; m <= 5; m++) {
                                if (gameEnvironment.gameGrid[m][column] == 0) movesLeft++;
                                else{
                                    break;
                                }
                            }
                        }

                    if (movesLeft != 0){
                        stateScore += determineScore(aiInARow, movesLeft);
                    }
                    aiInARow = 1;
                    blankCount = 0;
                }

                if (i >= 3) {
                    for (k = 1; k < 4; k++) {
                        if (gameEnvironment.gameGrid[i - k][j] == 1){
                            aiInARow++;
                        }
                        else if (gameEnvironment.gameGrid[i - k][j] == 2) {
                            aiInARow = 0;
                            break;
                        }
                    }
                    movesLeft = 0;

                    if (aiInARow > 0) {

                        int column = j;
                        for (int m = i - k + 1; m <= i - 1; m++) {
                            if (gameEnvironment.gameGrid[m][column] == 0) movesLeft++;
                            else{
                                break;
                            }
                        }
                    }
                    if (movesLeft != 0){
                        stateScore += determineScore(aiInARow, movesLeft);
                    }
                    aiInARow = 1;
                    blankCount = 0;
                }

                if (j >= 3) {
                    for (k = 1; k < 4; k++) {

                        if (gameEnvironment.gameGrid[i][j - k] == 1){
                            aiInARow++;
                        }
                        else if (gameEnvironment.gameGrid[i][j - k] == 2) {
                            aiInARow = 0;
                            blankCount = 0;
                            break;
                        } else{
                            blankCount++;
                        }
                    }
                    movesLeft = 0;
                    if (blankCount > 0)
                        for (int c = 1; c < 4; c++) {
                            int column = j - c;
                            for (int m = i; m <= 5; m++) {
                                if (gameEnvironment.gameGrid[m][column] == 0) movesLeft++;
                                else{
                                    break;
                                }
                            }
                        }

                    if (movesLeft != 0){
                        stateScore += determineScore(aiInARow, movesLeft);
                    }
                    aiInARow = 1;
                    blankCount = 0;
                }

                if (j <= 3 && i >= 3) {
                    for (k = 1; k < 4; k++) {
                        if (gameEnvironment.gameGrid[i - k][j + k] == 1){
                            aiInARow++;
                        }
                        else if (gameEnvironment.gameGrid[i - k][j + k] == 2) {
                            aiInARow = 0;
                            blankCount = 0;
                            break;
                        } else{
                            blankCount++;
                        }
                    }
                    movesLeft = 0;
                    if (blankCount > 0) {

                        for (int c = 1; c < 4; c++) {

                            int row = i - c;
                            int column = j + c;

                            for (int m = row; m <= 5; ++m) {
                                if (gameEnvironment.gameGrid[m][column] == 0){
                                    movesLeft++;
                                }
                                else if (gameEnvironment.gameGrid[m][column] == 1);
                                else{
                                    break;
                                }
                            }
                        }
                        if (movesLeft != 0){
                            stateScore += determineScore(aiInARow, movesLeft);
                        }
                        aiInARow = 1;
                        blankCount = 0;
                    }
                }

                if (i >= 3 && j >= 3) {

                    for (k = 1; k < 4; k++) {
                        if (gameEnvironment.gameGrid[i - k][j - k] == 1){

                            aiInARow++;
                        }
                        else if (gameEnvironment.gameGrid[i - k][j - k] == 2) {

                            aiInARow = 0;
                            blankCount = 0;
                            break;
                        } 
                        else{
                            blankCount++;
                        }
                    }
                    movesLeft = 0;
                    if (blankCount > 0) {
                        for (int c = 1; c < 4; c++) {
                            int column = j - c;
                            int row = i - c;
                            for (int m = row; m <= 5; ++m) {
                                if (gameEnvironment.gameGrid[m][column] == 0) movesLeft++;
                                else if (gameEnvironment.gameGrid[m][column] == 1);
                                else{
                                    break;
                                }
                            }
                        }
                        if (movesLeft != 0){
                            stateScore += determineScore(aiInARow, movesLeft);
                        }
                        aiInARow = 1;
                        blankCount = 0;
                    }
                }
            }
        }
        return stateScore;
    }

    public int minimaxAlgorithm(int depthMax, int turn, int alpha, int beta) {

        int maxScore = Integer.MIN_VALUE;
        int minScore = Integer.MAX_VALUE;
        int gameResult = evaluateState(gameEnvironment);

        if (beta <= alpha) {
            if (turn == 1){
                //AI top score
                return Integer.MAX_VALUE;
            }
            else{
                //Human top score
                return Integer.MIN_VALUE;
            }
        }




        if (gameResult == 1){

            return Integer.MAX_VALUE / 2;

        }
        else if (gameResult == 2){

            return Integer.MIN_VALUE / 2;

        }
        else if (gameResult == 0){

            return 0;

        }


        if (depthMax == GameAI.depthMax){

            return heuristicFunction(gameEnvironment);
        }



        for (int j = 0; j <= 6; j++) {

            int scoreCount = 0;

            if (!gameEnvironment.isMoveLegal(j)){
                continue;
            }

            if (turn == 1) {
                gameEnvironment.dropPiece(j, 1);
                scoreCount = minimaxAlgorithm(depthMax + 1, 2, alpha, beta);

                if (depthMax == 0) {

                    appendColoredText(GameFragment.textview_statistics,("AI score for location " + (j + 1) + " = " + scoreCount + "\n"), Color.rgb(210, 105, 30));
                    if (scoreCount > maxScore) finalAIMove = j;
                    if (scoreCount == Integer.MAX_VALUE / 2) {
                        gameEnvironment.undoLastMove(j);
                        break;
                    }
                }

                maxScore = Math.max(scoreCount, maxScore);
                alpha = Math.max(scoreCount, alpha);

            } else if (turn == 2) {
                gameEnvironment.dropPiece(j, 2);
                scoreCount = minimaxAlgorithm(depthMax + 1, 1, alpha, beta);
                minScore = Math.min(scoreCount, minScore);

                beta = Math.min(scoreCount, beta);
            }
            gameEnvironment.undoLastMove(j);

            if (scoreCount == Integer.MAX_VALUE || scoreCount == Integer.MIN_VALUE){
                break;
            }
        }
        return turn == 1 ? maxScore : minScore;
    }

    public int retrieveAIMoveLocation(){

        finalAIMove = -1;
        minimaxAlgorithm(0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return finalAIMove;

    }

    public static void appendColoredText(TextView tv, String text, int color) {
        int start = tv.getText().length();
        tv.append(text);
        int end = tv.getText().length();

        Spannable spannableText = (Spannable) tv.getText();
        spannableText.setSpan(new ForegroundColorSpan(color), start, end, 0);
    }


}
