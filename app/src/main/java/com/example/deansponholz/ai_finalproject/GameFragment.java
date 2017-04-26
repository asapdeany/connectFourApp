package com.example.deansponholz.ai_finalproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Service;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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


    private RadioGroup radioGroup;
    private System_UI_Manager system_ui_manager;
    static int moveFlag = 0;
    static boolean moveFirst, gameStarted, moveChosen;


    private ImageButton instructionsButton, playButton, restartButton, dropButton;

    private ImageView arrowRight_one, arrowRight_two, arrowRight_three;
    private TextView textview_play, textview_settings, textview_instruction, textview_statistics;

    private AlertDialog alertRestart, alertDifficulty, alertFirstMove;
    private int instruction_flag = 1;


    ImageView iv_11, iv_12, iv_13, iv_14, iv_15, iv_16, iv_17,
            iv_21, iv_22,iv_23, iv_24, iv_25, iv_26, iv_27,
            iv_31, iv_32, iv_33, iv_34, iv_35, iv_36, iv_37,
            iv_41, iv_42, iv_43, iv_44, iv_45, iv_46, iv_47,
            iv_51, iv_52, iv_53, iv_54, iv_55, iv_56, iv_57,
            iv_61, iv_62, iv_63, iv_64, iv_65, iv_66, iv_67;

    static ImageView[][] boardList;

    GameEnvironment gameEnvironment;

    String statistics_moves = new String();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gameplay, container, false);

        //hide status bar
        system_ui_manager = new System_UI_Manager(getActivity());

        //radioButtons
        radioGroup = (RadioGroup) root.findViewById(R.id.radioGroup_gameplay);
        radioGroup.setOnCheckedChangeListener(this);

        //arrows
        arrowRight_one = (ImageView) root.findViewById(R.id.arrowright_one);
        arrowRight_two = (ImageView) root.findViewById(R.id.arrowright_two);
        arrowRight_three = (ImageView) root.findViewById(R.id.arrowright_three);


        //textViews
        textview_play = (TextView) root.findViewById(R.id.textview_play);
        textview_instruction = (TextView) root.findViewById(R.id.textview_instruction);
        textview_settings = (TextView) root.findViewById(R.id.textview_settings);
        textview_statistics = (TextView) root.findViewById(R.id.textview_statistics);
        textview_statistics.setMovementMethod(new ScrollingMovementMethod());

        //Alert Dialogs
        AlertDialog.Builder builderRestart = alertFunctionRestart(root);
        alertRestart = builderRestart.create();
        alertRestart.setCanceledOnTouchOutside(false);
        AlertDialog.Builder builderDifficulty = alertFunctionDifficulty(root);
        alertDifficulty = builderDifficulty.create();
        alertDifficulty.setCanceledOnTouchOutside(false);

        //Buttons
        instructionsButton = (ImageButton) root.findViewById(R.id.button_instructions);
        instructionButtonListener(instructionsButton);
        playButton = (ImageButton) root.findViewById(R.id.button_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDifficulty.show();
                //alertFirstMove.show();
            }
        });
        restartButton = (ImageButton) root.findViewById(R.id.button_settings);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //ToDo - clear gameboard

                alertRestart.show();
            }
        });

        dropButton = (ImageButton) root.findViewById(R.id.button_dropbutton);
        dropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gameEnvironment.dropPiece(moveFlag, 1) == true){
                    textview_statistics.append((("Human Move at Column: ") + moveFlag) + "\n" );
                }
                else {
                    Toast.makeText(getActivity(), "Move Not Allowed!!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        initializeGameBoard(root);
        //textview_statistics.setText(Integer.toString(boardList.size()));

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
                moveChosen = true;
                break;
            case R.id.radioButton2:
                Log.d("onCheckChanged", Integer.toString(6));
                moveFlag = 5;
                moveChosen = true;
                break;
            case R.id.radioButton3:
                Log.d("onCheckChanged", Integer.toString(5));
                moveFlag = 4;
                moveChosen = true;
                break;
            case R.id.radioButton4:
                Log.d("onCheckChanged", Integer.toString(4));
                moveFlag = 3;
                moveChosen = true;
                break;
            case R.id.radioButton5:
                Log.d("onCheckChanged", Integer.toString(3));
                moveFlag = 2;
                moveChosen = true;
                break;
            case R.id.radioButton6:
                Log.d("onCheckChanged", Integer.toString(2));
                moveFlag = 1;
                moveChosen = true;
                break;
            case R.id.radioButton7:
                Log.d("onCheckChanged", Integer.toString(1));
                moveFlag = 0;
                moveChosen = true;
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

    public void instructionButtonListener(ImageButton imageButton){

        final Animation myFadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (instruction_flag == 0){

                    arrowRight_one.clearAnimation();
                    arrowRight_one.setVisibility(View.INVISIBLE);

                    arrowRight_two.clearAnimation();
                    arrowRight_two.setVisibility(View.INVISIBLE);

                    arrowRight_three.clearAnimation();
                    arrowRight_three.setVisibility(View.INVISIBLE);

                    textview_play.clearAnimation();
                    textview_play.setVisibility(View.INVISIBLE);

                    textview_settings.clearAnimation();
                    textview_settings.setVisibility(View.INVISIBLE);

                    textview_instruction.clearAnimation();
                    textview_instruction.setVisibility(View.INVISIBLE);

                    instruction_flag = 1;
                }
                else if (instruction_flag == 1){

                    arrowRight_one.startAnimation(myFadeInAnimation);
                    arrowRight_one.setVisibility(View.VISIBLE);

                    arrowRight_two.startAnimation(myFadeInAnimation);
                    arrowRight_two.setVisibility(View.VISIBLE);

                    arrowRight_three.startAnimation(myFadeInAnimation);
                    arrowRight_three.setVisibility(View.VISIBLE);

                    textview_play.startAnimation(myFadeInAnimation);
                    textview_play.setVisibility(View.VISIBLE);

                    textview_settings.startAnimation(myFadeInAnimation);
                    textview_settings.setVisibility(View.VISIBLE);

                    textview_instruction.startAnimation(myFadeInAnimation);
                    textview_instruction.setVisibility(View.VISIBLE);

                    instruction_flag = 0;
                }


            }
        });
    }

    public AlertDialog.Builder alertFunctionRestart(View root){

        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());

        builder.setTitle("Confirm Restart");
        builder.setMessage("Are you would like to restart? \n (All Progress will be lost)");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                GameEnvironment.clearBoard();
                textview_statistics.setText("");
                dialog.dismiss();
                system_ui_manager.hideStatusBar(getActivity());
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

    //This is where the game begins
    public AlertDialog.Builder alertFunctionDifficulty(final View root){

        //following code will be in your activity.java file
        final CharSequence[] items = {" Easy "," Medium "," Hard "};
        // arraylist to keep the selected items
        final ArrayList seletedItems=new ArrayList();

        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
        builder.setTitle("Select The Difficulty Level");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Difficulty option", Integer.toString(which));
            }
        });
        // Set the action buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //  Your code when user clicked on OK
                //  You can write the code  to save the selected item here

                AlertDialog.Builder builderMoveFirst = alertFunctionMoveFirst(root);
                alertFirstMove = builderMoveFirst.create();
                alertFirstMove.setCanceledOnTouchOutside(false);
                alertFirstMove.show();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //  Your code when user clicked on Cancel
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


                playButton.setClickable(false);
                //Start Game

                dropButton.setVisibility(View.VISIBLE);
                textview_statistics.setVisibility(View.VISIBLE);

                gameEnvironment = new GameEnvironment();
                GameAI gameAI = new GameAI(gameEnvironment);
                //gameAI.playAgainstAIConsole();

                dialog.dismiss();
                system_ui_manager.hideStatusBar(getActivity());
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dropButton.setVisibility(View.VISIBLE);
                textview_statistics.setVisibility(View.VISIBLE);

                //Start game
                gameEnvironment = new GameEnvironment();
                GameAI gameAI = new GameAI(gameEnvironment);
                //gameAI.playAgainstAIConsole();

                dialog.dismiss();
                system_ui_manager.hideStatusBar(getActivity());
            }
        });

        return builder;

    }

    public void startGame(){


        /*
        b.displayBoard();
        b.placeMove(3, 1);
        b.displayBoard();

        while(true){
            letHumanMove();
            b.displayBoard();

            int gameResult = gameResult(b);
            if(gameResult==1){System.out.println("AI Wins!");break;}
            else if(gameResult==2){System.out.println("You Win!");break;}
            else if(gameResult==0){System.out.println("Draw!");break;}

            b.placeMove(getAIMove(), 1);
            b.displayBoard();
            gameResult = gameResult(b);
            if(gameResult==1){System.out.println("AI Wins!");break;}
            else if(gameResult==2){System.out.println("You Win!");break;}
            else if(gameResult==0){System.out.println("Draw!");break;}

        }
        */


    }
    @Override
    public void onResume() {
        system_ui_manager.hideStatusBar(getActivity());
        super.onResume();
    }
}
