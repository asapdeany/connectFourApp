package com.example.deansponholz.ai_finalproject;

import android.util.Log;
import android.view.View;

import java.util.Scanner;

/**
 * Created by deansponholz on 4/8/17.
 */

public class GameAI {

    //Local GameVironment instance
    private GameEnvironment gameEnvironment;

    //
    private int nextMoveSpot = -1;
    //Depth max for Minimax
    private int depthMax = 9;


    //Constructor - creates instance of GameAI
    public GameAI(GameEnvironment gameEnvironment){
        this.gameEnvironment = gameEnvironment;
    }

    public void letHumanMove(){

        /*
        Scanner scan = new Scanner(System.in);
        System.out.println("Your move (1-7): ");
        int move = scan.nextInt();
        while(move<1 || move > 7 || !gameEnvironment.isMoveLegal(move-1)){
            System.out.println("Invalid move.\n\nYour move (1-7): ");
            move = scan.nextInt();
        }
        */

        //Assume 2 is the opponent

        gameEnvironment.dropPiece(2, (byte)2);
        /*
        int move = moveFlag;
        if (GameFragment.moveChosen == true) {
            gameEnvironment.dropPiece(move, (byte) 2);
            GameFragment.moveChosen = false;
            GameFragment.dropButton.setVisibility(View.INVISIBLE);
            GameFragment.radioGroup.setVisibility(View.INVISIBLE);
        }
        */
    }

    //Game Result -
    //Investigate a game state and see whether it terminal
    //Returns Either 1, 2, 0
    public int gameResult(GameEnvironment gameEnvironment){


        int aiScore = 0;
        int humanScore = 0;
        for(int i=5;i>=0;i--){
            for(int j=0;j<=6;j++){
                if(gameEnvironment.gameGrid[i][j]==0){
                    continue;
                }
                //Checking cells to the right of current location
                if(j<=3){
                    for(int k=0;k<4;k++){
                        if(gameEnvironment.gameGrid[i][j+k]==1){
                            aiScore++;
                        }
                        else if(gameEnvironment.gameGrid[i][j+k]==2){
                            humanScore++;
                        }
                        else break;
                    }
                    if(aiScore==4){
                        //Log.d("gameResult", "AI");
                        return 1;
                    }
                    else if (humanScore==4){
                        //Log.d("gameResult", "HUMAN");
                        return 2;
                    }
                    aiScore = 0;
                    humanScore = 0;
                }

                //Checking cells up of current location
                if(i>=3){
                    for(int k=0;k<4;++k){
                        if(gameEnvironment.gameGrid[i-k][j]==1){
                            aiScore++;
                        }
                        else if(gameEnvironment.gameGrid[i-k][j]==2){
                            humanScore++;
                        }
                        else break;
                    }
                    if(aiScore==4){
                        //Log.d("gameResult", "AI");
                        return 1;
                    }
                    else if (humanScore==4){
                        //Log.d("gameResult", "HUMAN");
                        return 2;
                    }
                    aiScore = 0;
                    humanScore = 0;
                }

                //Checking diagonal up-right of current location
                if(j<=3 && i>= 3){
                    for(int k=0;k<4;++k){
                        if(gameEnvironment.gameGrid[i-k][j+k]==1){
                            aiScore++;
                        }
                        else if(gameEnvironment.gameGrid[i-k][j+k]==2){

                            humanScore++;
                        }
                        else break;
                    }
                    if(aiScore==4){
                        //Log.d("gameResult", "AI");
                        return 1;
                    }
                    else if (humanScore==4){
                        //Log.d("gameResult", "HUMAN");
                        return 2;
                    }
                    aiScore = 0;
                    humanScore = 0;
                }

                //Checking diagonal up-left of current location
                if(j>=3 && i>=3){
                    for(int k=0;k<4;++k){
                        if(gameEnvironment.gameGrid[i-k][j-k]==1){
                            aiScore++;
                        }
                        else if(gameEnvironment.gameGrid[i-k][j-k]==2){
                            humanScore++;
                        }
                        else break;
                    }
                    if(aiScore==4){
                        //Log.d("gameResult", "AI");
                        return 1;
                    }
                    else if (humanScore==4){
                        //Log.d("gameResult", "HUMAN");
                        return 2;
                    }
                    aiScore = 0;
                    humanScore = 0;
                }
            }
        }

        for(int j=0;j<7;++j){
            //Game has not ended yet
            if(gameEnvironment.gameGrid[0][j]==0){
                return -1;
            }
        }
        //Game draw!
        return 0;
    }

    public int calculateScore(int aiScore, int moreMoves){
        int moveScore = 4 - moreMoves;
        if(aiScore==0){
            return 0;
        }
        else if(aiScore==1){
            return 1*moveScore;
        }
        else if(aiScore==2){
            return 10*moveScore;
        }
        else if(aiScore==3){
            return 100*moveScore;
        }
        else return 1000;
    }

    //Evaluate board favorableness for AI
    public int evaluateBoard(GameEnvironment gameEnvironment) {

        int aiScore = 1;
        int score = 0;
        int blanks = 0;
        int k = 0, moreMoves = 0;
        for (int i = 5; i >= 0; --i) {
            for (int j = 0; j <= 6; ++j) {

                if (gameEnvironment.gameGrid[i][j] == 0 || gameEnvironment.gameGrid[i][j] == 2) continue;

                if (j <= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (gameEnvironment.gameGrid[i][j + k] == 1) aiScore++;
                        else if (gameEnvironment.gameGrid[i][j + k] == 2) {
                            aiScore = 0;
                            blanks = 0;
                            break;
                        } else blanks++;
                    }

                    moreMoves = 0;
                    if (blanks > 0)
                        for (int c = 1; c < 4; ++c) {
                            int column = j + c;
                            for (int m = i; m <= 5; m++) {
                                if (gameEnvironment.gameGrid[m][column] == 0) moreMoves++;
                                else break;
                            }
                        }

                    if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                    aiScore = 1;
                    blanks = 0;
                }

                if (i >= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (gameEnvironment.gameGrid[i - k][j] == 1) aiScore++;
                        else if (gameEnvironment.gameGrid[i - k][j] == 2) {
                            aiScore = 0;
                            break;
                        }
                    }
                    moreMoves = 0;

                    if (aiScore > 0) {
                        int column = j;
                        for (int m = i - k + 1; m <= i - 1; m++) {
                            if (gameEnvironment.gameGrid[m][column] == 0) moreMoves++;
                            else break;
                        }
                    }
                    if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                    aiScore = 1;
                    blanks = 0;
                }

                if (j >= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (gameEnvironment.gameGrid[i][j - k] == 1) aiScore++;
                        else if (gameEnvironment.gameGrid[i][j - k] == 2) {
                            aiScore = 0;
                            blanks = 0;
                            break;
                        } else blanks++;
                    }
                    moreMoves = 0;
                    if (blanks > 0)
                        for (int c = 1; c < 4; ++c) {
                            int column = j - c;
                            for (int m = i; m <= 5; m++) {
                                if (gameEnvironment.gameGrid[m][column] == 0) moreMoves++;
                                else break;
                            }
                        }

                    if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                    aiScore = 1;
                    blanks = 0;
                }

                if (j <= 3 && i >= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (gameEnvironment.gameGrid[i - k][j + k] == 1) aiScore++;
                        else if (gameEnvironment.gameGrid[i - k][j + k] == 2) {
                            aiScore = 0;
                            blanks = 0;
                            break;
                        } else blanks++;
                    }
                    moreMoves = 0;
                    if (blanks > 0) {
                        for (int c = 1; c < 4; ++c) {
                            int column = j + c, row = i - c;
                            for (int m = row; m <= 5; ++m) {
                                if (gameEnvironment.gameGrid[m][column] == 0) moreMoves++;
                                else if (gameEnvironment.gameGrid[m][column] == 1) ;
                                else break;
                            }
                        }
                        if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                        aiScore = 1;
                        blanks = 0;
                    }
                }

                if (i >= 3 && j >= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (gameEnvironment.gameGrid[i - k][j - k] == 1) aiScore++;
                        else if (gameEnvironment.gameGrid[i - k][j - k] == 2) {
                            aiScore = 0;
                            blanks = 0;
                            break;
                        } else blanks++;
                    }
                    moreMoves = 0;
                    if (blanks > 0) {
                        for (int c = 1; c < 4; ++c) {
                            int column = j - c, row = i - c;
                            for (int m = row; m <= 5; ++m) {
                                if (gameEnvironment.gameGrid[m][column] == 0) moreMoves++;
                                else if (gameEnvironment.gameGrid[m][column] == 1) ;
                                else break;
                            }
                        }
                        if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                        aiScore = 1;
                        blanks = 0;
                    }
                }
            }
        }
        return score;
    }

    public int minimax(int depth, int turn, int alpha, int beta) {

        if (beta <= alpha) {
            if (turn == 1) return Integer.MAX_VALUE;
            else return Integer.MIN_VALUE;
        }
        int gameResult = gameResult(gameEnvironment);

        if (gameResult == 1) return Integer.MAX_VALUE / 2;
        else if (gameResult == 2) return Integer.MIN_VALUE / 2;
        else if (gameResult == 0) return 0;

        if (depth == depthMax) return evaluateBoard(gameEnvironment);

        int maxScore = Integer.MIN_VALUE, minScore = Integer.MAX_VALUE;

        for (int j = 0; j <= 6; ++j) {

            int currentScore = 0;

            if (!gameEnvironment.isMoveLegal(j)) continue;

            if (turn == 1) {
                gameEnvironment.dropPiece(j, 1);
                currentScore = minimax(depth + 1, 2, alpha, beta);

                if (depth == 0) {
                    System.out.println("Score for location " + j + " = " + currentScore);
                    if (currentScore > maxScore) nextMoveSpot = j;
                    if (currentScore == Integer.MAX_VALUE / 2) {
                        gameEnvironment.undoLastMove(j);
                        break;
                    }
                }

                maxScore = Math.max(currentScore, maxScore);

                alpha = Math.max(currentScore, alpha);
            } else if (turn == 2) {
                gameEnvironment.dropPiece(j, 2);
                currentScore = minimax(depth + 1, 1, alpha, beta);
                minScore = Math.min(currentScore, minScore);

                beta = Math.min(currentScore, beta);
            }
            gameEnvironment.undoLastMove(j);
            if (currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) break;
        }
        return turn == 1 ? maxScore : minScore;
    }

    public int getAIMove(){
        nextMoveSpot= -1;
        minimax(0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return nextMoveSpot;
    }



    public void startGame(){


        if (GameFragment.moveFirst == true){
            letHumanMove();
            gameEnvironment.updateUI();
            GameFragment.moveChosen = false;


        }
        else if (GameFragment.moveFirst == false) {
            gameEnvironment.updateUI();
            gameEnvironment.dropPiece(3, 1);
            gameEnvironment.updateUI();
        }




        while(true){

            letHumanMove();
            gameEnvironment.updateUI();

            System.out.println(getAIMove());
            int gameResult = gameResult(gameEnvironment);
            if(gameResult==1){System.out.println("AI Wins!");break;}
            else if(gameResult==2){System.out.println("You Win!");break;}
            else if(gameResult==0){System.out.println("Draw!");break;}

            

            gameEnvironment.dropPiece(getAIMove(), 1);
            gameEnvironment.updateUI();
            gameResult = gameResult(gameEnvironment);
            if(gameResult==1){System.out.println("AI Wins!");break;}
            else if(gameResult==2){System.out.println("You Win!");break;}
            else if(gameResult==0){System.out.println("Draw!");break;}
            }



    }



}
