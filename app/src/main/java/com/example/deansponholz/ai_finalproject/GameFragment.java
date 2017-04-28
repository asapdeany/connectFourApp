package com.example.deansponholz.ai_finalproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Service;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deansponholz on 4/8/17.
 */

public class GameFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{


    public static RadioGroup radioGroup;
    private System_UI_Manager system_ui_manager;
    static int moveFlag = 0;
    static boolean moveFirst, gameOver, moveChosen;


    private ImageButton instructionsButton, playButton, restartButton;
    public static ImageButton dropButton;

    private ImageView arrowRight_one, arrowRight_two, arrowRight_three;
    private TextView textview_play, textview_settings, textview_instruction;
    public static TextView textview_statistics;

    private AlertDialog alertRestart, alertFirstMove, alertDifficulty;
    private int instruction_flag = 1;


    ImageView iv_11, iv_12, iv_13, iv_14, iv_15, iv_16, iv_17,
            iv_21, iv_22,iv_23, iv_24, iv_25, iv_26, iv_27,
            iv_31, iv_32, iv_33, iv_34, iv_35, iv_36, iv_37,
            iv_41, iv_42, iv_43, iv_44, iv_45, iv_46, iv_47,
            iv_51, iv_52, iv_53, iv_54, iv_55, iv_56, iv_57,
            iv_61, iv_62, iv_63, iv_64, iv_65, iv_66, iv_67;

    static ImageView[][] boardList;

    private GameEnvironment gameEnvironment;
    private GameAI gameAI;
    private int gameResult;

    String statistics_moves = new String();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gameplay, container, false);

        //hide status bar
        system_ui_manager = new System_UI_Manager(getActivity());

        //radioButtons
        radioGroup = (RadioGroup) root.findViewById(R.id.radioGroup_gameplay);
        radioGroup.setOnCheckedChangeListener(this);

        //textview
        textview_statistics = (TextView) root.findViewById(R.id.textview_statistics);
        textview_statistics.setMovementMethod(new ScrollingMovementMethod());

        initializeGameBoard(root);

        //Alert Dialogs
        AlertDialog.Builder builderRestart = alertFunctionRestart(root);
        alertRestart = builderRestart.create();
        alertRestart.setCanceledOnTouchOutside(false);


        AlertDialog.Builder builderFirstMove = alertFunctionMoveFirst(root);
        alertFirstMove = builderFirstMove.create();
        alertFirstMove.setCanceledOnTouchOutside(false);

        AlertDialog.Builder builderDifficulty = alertFunctionDifficulty(root);
        alertDifficulty = builderDifficulty.create();
        alertDifficulty.setCanceledOnTouchOutside(false);


        playButton = (ImageButton) root.findViewById(R.id.button_play);
        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                alertDifficulty.show();
            }
        });

        restartButton = (ImageButton) root.findViewById(R.id.button_settings);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertRestart.show();
            }
        });


        dropButton = (ImageButton) root.findViewById(R.id.button_dropbutton);
        dropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dropButton.setClickable(false);
                int humanMove = moveFlag;
                if (gameEnvironment.isMoveLegal(humanMove) == true){

                    if (gameOver == false) {


                        gameEnvironment.dropPiece(humanMove, 2);
                        appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 255));
                        appendColoredText(textview_statistics, ("HUMAN Move at Column: " + (humanMove + 1) + "\n"), Color.rgb(255, 0, 255));
                        appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 255));
                        gameEnvironment.updateUI();
                        gameResult = gameAI.gameResult(gameEnvironment);

                        if (gameResult == 1) {
                            //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 0));
                            appendColoredText(textview_statistics, ("---------------------AI WINS!!!-------------------" + "\n"), Color.rgb(255, 0, 0));
                            //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 0));
                            dropButton.setVisibility(View.INVISIBLE);
                            gameOver = true;
                            //GAMEOVER
                        } else if (gameResult == 2) {
                            //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 0));
                            appendColoredText(textview_statistics, ("----------------HUMAN WINS!!!-----------------" + "\n"), Color.rgb(255, 0, 0));
                            //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 0));
                            dropButton.setVisibility(View.INVISIBLE);
                            gameOver = true;
                        } else if (gameResult == 0) {
                            //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 0));
                            appendColoredText(textview_statistics, ("--------------------GAME DRAW!!!--------------------" + "\n"), Color.rgb(255, 0, 0));
                            //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 0));
                            dropButton.setVisibility(View.INVISIBLE);
                            gameOver = true;
                        }
                    }


                    //AI RESPONSE
                    if (gameOver == false) {
                        //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(210, 105, 30));
                        appendColoredText(textview_statistics, ("THINKING..." + "\n"), Color.rgb(210, 105, 30));
                        //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(210, 105, 30));
                        int move = gameAI.getAIMove();
                        //Log.d("MOVE", Integer.toString(move));
                        gameEnvironment.dropPiece(move, 1);
                        appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 255));
                        appendColoredText(textview_statistics, (("AI Move at Column: ") + (move + 1) + "\n"), Color.rgb(255, 0, 255));
                        appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 255));


                        gameEnvironment.updateUI();
                        gameResult = gameAI.gameResult(gameEnvironment);

                        if (gameResult == 1) {
                            //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 0));
                            appendColoredText(textview_statistics, ("---------------------AI WINS!!!-------------------" + "\n"), Color.rgb(255, 0, 0));
                            //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 0));
                            dropButton.setVisibility(View.INVISIBLE);
                            gameOver = true;
                            //GAMEOVER
                        } else if (gameResult == 2) {
                            //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 0));
                            appendColoredText(textview_statistics, ("----------------HUMAN WINS!!!-----------------" + "\n"), Color.rgb(255, 0, 0));
                            //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 0));
                            dropButton.setVisibility(View.INVISIBLE);
                            gameOver = true;
                        } else if (gameResult == 0) {
                            //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 0));
                            appendColoredText(textview_statistics, ("--------------------GAME DRAW!!!--------------------" + "\n"), Color.rgb(255, 0, 0));
                            //appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 0));
                            dropButton.setVisibility(View.INVISIBLE);
                            gameOver = true;
                        }
                    }

                }
                else {
                    Toast.makeText(getActivity(), "ILLEGAL MOVE - TRY AGAIN",
                            Toast.LENGTH_SHORT).show();
                }

                dropButton.setClickable(true);
            }
        });

        return root;
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch(checkedId) {
            case R.id.radioButton:
                Log.d("onCheckChanged", Integer.toString(7));
                moveFlag = 6;
                break;
            case R.id.radioButton2:
                Log.d("onCheckChanged", Integer.toString(6));
                moveFlag = 5;
                break;
            case R.id.radioButton3:
                Log.d("onCheckChanged", Integer.toString(5));
                moveFlag = 4;
                break;
            case R.id.radioButton4:
                Log.d("onCheckChanged", Integer.toString(4));
                moveFlag = 3;
                break;
            case R.id.radioButton5:
                Log.d("onCheckChanged", Integer.toString(3));
                moveFlag = 2;
                break;
            case R.id.radioButton6:
                Log.d("onCheckChanged", Integer.toString(2));
                moveFlag = 1;
                break;
            case R.id.radioButton7:
                Log.d("onCheckChanged", Integer.toString(1));
                moveFlag = 0;
                break;
        }
    }

    public void initializeGameBoard(View root){

        boardList = new ImageView[6][7];

        iv_11 = (ImageView) root.findViewById(R.id.iv_11);
        iv_12 = (ImageView) root.findViewById(R.id.iv_12);
        iv_13 = (ImageView) root.findViewById(R.id.iv_13);
        iv_14 = (ImageView) root.findViewById(R.id.iv_14);
        iv_15 = (ImageView) root.findViewById(R.id.iv_15);
        iv_16 = (ImageView) root.findViewById(R.id.iv_16);
        iv_17 = (ImageView) root.findViewById(R.id.iv_17);


        iv_21 = (ImageView) root.findViewById(R.id.iv_21);
        iv_22 = (ImageView) root.findViewById(R.id.iv_22);
        iv_23 = (ImageView) root.findViewById(R.id.iv_23);
        iv_24 = (ImageView) root.findViewById(R.id.iv_24);
        iv_25 = (ImageView) root.findViewById(R.id.iv_25);
        iv_26 = (ImageView) root.findViewById(R.id.iv_26);
        iv_27 = (ImageView) root.findViewById(R.id.iv_27);

        iv_31 = (ImageView) root.findViewById(R.id.iv_31);
        iv_32 = (ImageView) root.findViewById(R.id.iv_32);
        iv_33 = (ImageView) root.findViewById(R.id.iv_33);
        iv_34 = (ImageView) root.findViewById(R.id.iv_34);
        iv_35 = (ImageView) root.findViewById(R.id.iv_35);
        iv_36 = (ImageView) root.findViewById(R.id.iv_36);
        iv_37 = (ImageView) root.findViewById(R.id.iv_37);

        iv_41 = (ImageView) root.findViewById(R.id.iv_41);
        iv_42 = (ImageView) root.findViewById(R.id.iv_42);
        iv_43 = (ImageView) root.findViewById(R.id.iv_43);
        iv_44 = (ImageView) root.findViewById(R.id.iv_44);
        iv_45 = (ImageView) root.findViewById(R.id.iv_45);
        iv_46 = (ImageView) root.findViewById(R.id.iv_46);
        iv_47 = (ImageView) root.findViewById(R.id.iv_47);

        iv_51 = (ImageView) root.findViewById(R.id.iv_51);
        iv_52 = (ImageView) root.findViewById(R.id.iv_52);
        iv_53 = (ImageView) root.findViewById(R.id.iv_53);
        iv_54 = (ImageView) root.findViewById(R.id.iv_54);
        iv_55 = (ImageView) root.findViewById(R.id.iv_55);
        iv_56 = (ImageView) root.findViewById(R.id.iv_56);
        iv_57 = (ImageView) root.findViewById(R.id.iv_57);

        iv_61 = (ImageView) root.findViewById(R.id.iv_61);
        iv_62 = (ImageView) root.findViewById(R.id.iv_62);
        iv_63 = (ImageView) root.findViewById(R.id.iv_63);
        iv_64 = (ImageView) root.findViewById(R.id.iv_64);
        iv_65 = (ImageView) root.findViewById(R.id.iv_65);
        iv_66 = (ImageView) root.findViewById(R.id.iv_66);
        iv_67 = (ImageView) root.findViewById(R.id.iv_67);

        boardList[0][0] = (iv_61);
        boardList[0][1] = (iv_62);
        boardList[0][2] = (iv_63);
        boardList[0][3] = (iv_64);
        boardList[0][4] = (iv_65);
        boardList[0][5] = (iv_66);
        boardList[0][6] = (iv_67);

        boardList[1][0] = (iv_51);
        boardList[1][1] = (iv_52);
        boardList[1][2] = (iv_53);
        boardList[1][3] = (iv_54);
        boardList[1][4] = (iv_55);
        boardList[1][5] = (iv_56);
        boardList[1][6] = (iv_57);

        boardList[2][0] = (iv_41);
        boardList[2][1] = (iv_42);
        boardList[2][2] = (iv_43);
        boardList[2][3] = (iv_44);
        boardList[2][4] = (iv_45);
        boardList[2][5] = (iv_46);
        boardList[2][6] = (iv_47);

        boardList[3][0] = (iv_31);
        boardList[3][1] = (iv_32);
        boardList[3][2] = (iv_33);
        boardList[3][3] = (iv_34);
        boardList[3][4] = (iv_35);
        boardList[3][5] = (iv_36);
        boardList[3][6] = (iv_37);

        boardList[4][0] = (iv_21);
        boardList[4][1] = (iv_22);
        boardList[4][2] = (iv_23);
        boardList[4][3] = (iv_24);
        boardList[4][4] = (iv_25);
        boardList[4][5] = (iv_26);
        boardList[4][6] = (iv_27);

        boardList[5][0] = (iv_11);
        boardList[5][1] = (iv_12);
        boardList[5][2] = (iv_13);
        boardList[5][3] = (iv_14);
        boardList[5][4] = (iv_15);
        boardList[5][5] = (iv_16);
        boardList[5][6] = (iv_17);

    }


    public AlertDialog.Builder alertFunctionRestart(View root) {

        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());

        builder.setTitle("Confirm Restart");
        builder.setMessage("Are you would like to restart? \n (All Progress will be lost)");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                try{
                    gameEnvironment.clearBoard();
                }
                catch (NullPointerException e){
                    Toast.makeText(getActivity(), "Game Has Not Started",
                            Toast.LENGTH_LONG).show();
                }
                textview_statistics.setText("");
                dropButton.setVisibility(View.INVISIBLE);
                playButton.setClickable(true);
                dialog.dismiss();
                system_ui_manager.hideStatusBar(getActivity());
                gameOver = false;

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
                system_ui_manager.hideStatusBar(getActivity());
            }
        });
        return builder;
    }

    public AlertDialog.Builder alertFunctionMoveFirst(View root){

        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());

        builder.setTitle("Would you like to move first?");
        //builder.setMessage("Are you would like to restart? \n (All Progress will be lost)");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                //set UI visibility
                dropButton.setVisibility(View.VISIBLE);
                textview_statistics.setVisibility(View.VISIBLE);

                //set moveFlag to TRUE
                moveFirst = true;

                //cant start new instance during gameplay
                playButton.setClickable(false);

                dialog.dismiss();
                system_ui_manager.hideStatusBar(getActivity());

                gameOver = false;
                //gameEnvironment = new GameEnvironment();
                //gameAI = new GameAI(gameEnvironment);
                //gameAI.startGame();


            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //set UI visibility
                dropButton.setVisibility(View.VISIBLE);
                textview_statistics.setVisibility(View.VISIBLE);

                //set moveFlag to FALSE
                moveFirst = false;

                //cant start new instance during gameplay
                playButton.setClickable(false);

                //textview_statistics.append((("AI Move at Column: ") + 3) + "\n" );

                dialog.dismiss();
                system_ui_manager.hideStatusBar(getActivity());


                //start Game
                gameOver = false;

                gameEnvironment.dropPiece(3, 1);
                gameEnvironment.updateUI();
                appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 255));
                appendColoredText(textview_statistics, (("AI Move at Column: ") + (3 + 1) + "\n"), Color.rgb(255, 0, 255));
                appendColoredText(textview_statistics, ("----------------------------------------------------" + "\n"), Color.rgb(255, 0, 255));


            }
        });

        return builder;

    }

    //This is where the game begins
    public AlertDialog.Builder alertFunctionDifficulty(final View root){

        final CharSequence[] items = {" Easy (Max Depth = 5) "," Hard (Max Depth = 9) "};
        //arraylist to keep the selected items
        final ArrayList seletedItems=new ArrayList();


        gameEnvironment = new GameEnvironment();
        gameAI = new GameAI(gameEnvironment);
        gameAI.depthMax = 5;

        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
        builder.setTitle("Select The Difficulty Level");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Log.d("Difficulty option", Integer.toString(which));
            if (which == 0){
                gameAI.depthMax = 5;
            }
            else if (which == 1){
                gameAI.depthMax = 9;
            }
        }
        });
         //Set the action buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
             //  Your code when user clicked on OK
             //  You can write the code  to save the selected item here
                alertFirstMove.show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int id) {
            //Your code when user clicked on Cancel
            system_ui_manager.hideStatusBar(getActivity());
            }
        });
        return builder;
    }

    public static void appendColoredText(TextView tv, String text, int color) {
        int start = tv.getText().length();
        tv.append(text);
        int end = tv.getText().length();

        Spannable spannableText = (Spannable) tv.getText();
        spannableText.setSpan(new ForegroundColorSpan(color), start, end, 0);
    }

    @Override
    public void onResume() {
        system_ui_manager.hideStatusBar(getActivity());
        super.onResume();
    }
}
