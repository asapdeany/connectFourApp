package com.example.deansponholz.ai_finalproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Service;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
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

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deansponholz on 4/8/17.
 */

public class GameFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{


    private RadioGroup radioGroup;
    private System_UI_Manager system_ui_manager;
    static int moveFlag = -1;


    private ImageButton instructionsButton, playButton, settingsButton, undoButton;

    private ImageView arrowRight_one, arrowRight_two, arrowRight_three, arrow_undo;
    private TextView textview_play, textview_settings, textview_instruction, textview_undo, textview_statistics;

    private AlertDialog alert;
    private int instruction_flag = 1;

    ArrayList<ImageView> boardList = new ArrayList<>();

    ImageView iv_11, iv_12, iv_13, iv_14, iv_15, iv_16, iv_17,
            iv_21, iv_22,iv_23, iv_24, iv_25, iv_26, iv_27,
            iv_31, iv_32, iv_33, iv_34, iv_35, iv_36, iv_37,
            iv_41, iv_42, iv_43, iv_44, iv_45, iv_46, iv_47,
            iv_51, iv_52, iv_53, iv_54, iv_55, iv_56, iv_57,
            iv_61, iv_62, iv_63, iv_64, iv_65, iv_66, iv_67;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gameplay, container, false);

        radioGroup = (RadioGroup) root.findViewById(R.id.radioGroup_gameplay);
        radioGroup.setOnCheckedChangeListener(this);

        final Animation myFadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        //final Animation myFadeOutAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout);

        arrowRight_one = (ImageView) root.findViewById(R.id.arrowright_one);
        arrowRight_two = (ImageView) root.findViewById(R.id.arrowright_two);
        arrowRight_three = (ImageView) root.findViewById(R.id.arrowright_three);
        arrow_undo = (ImageView) root.findViewById(R.id.arrow_undo);

        textview_play = (TextView) root.findViewById(R.id.textview_play);
        textview_instruction = (TextView) root.findViewById(R.id.textview_instruction);
        textview_settings = (TextView) root.findViewById(R.id.textview_settings);
        textview_undo = (TextView) root.findViewById(R.id.textview_undo);
        textview_statistics = (TextView) root.findViewById(R.id.textview_statistics);

        playButton = (ImageButton) root.findViewById(R.id.button_play);
        playButton = (ImageButton) root.findViewById(R.id.button_play);
        playButton = (ImageButton) root.findViewById(R.id.button_play);
        instructionsButton = (ImageButton) root.findViewById(R.id.button_instructions);
        undoButton = (ImageButton) root.findViewById(R.id.button_undo);


        instructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (instruction_flag == 0){

                    arrowRight_one.clearAnimation();
                    arrowRight_one.setVisibility(View.INVISIBLE);

                    arrowRight_two.clearAnimation();
                    arrowRight_two.setVisibility(View.INVISIBLE);

                    arrowRight_three.clearAnimation();
                    arrowRight_three.setVisibility(View.INVISIBLE);

                    arrow_undo.clearAnimation();
                    arrow_undo.setVisibility(View.INVISIBLE);

                    textview_play.clearAnimation();
                    textview_play.setVisibility(View.INVISIBLE);

                    textview_settings.clearAnimation();
                    textview_settings.setVisibility(View.INVISIBLE);

                    textview_instruction.clearAnimation();
                    textview_instruction.setVisibility(View.INVISIBLE);

                    textview_undo.clearAnimation();
                    textview_undo.setVisibility(View.INVISIBLE);
                    instruction_flag = 1;
                }
                else if (instruction_flag == 1){

                    arrowRight_one.startAnimation(myFadeInAnimation);
                    arrowRight_one.setVisibility(View.VISIBLE);

                    arrowRight_two.startAnimation(myFadeInAnimation);
                    arrowRight_two.setVisibility(View.VISIBLE);

                    arrowRight_three.startAnimation(myFadeInAnimation);
                    arrowRight_three.setVisibility(View.VISIBLE);

                    arrow_undo.startAnimation(myFadeInAnimation);
                    arrow_undo.setVisibility(View.VISIBLE);

                    textview_play.startAnimation(myFadeInAnimation);
                    textview_play.setVisibility(View.VISIBLE);

                    textview_settings.startAnimation(myFadeInAnimation);
                    textview_settings.setVisibility(View.VISIBLE);

                    textview_instruction.startAnimation(myFadeInAnimation);
                    textview_instruction.setVisibility(View.VISIBLE);

                    textview_undo.startAnimation(myFadeInAnimation);
                    textview_undo.setVisibility(View.VISIBLE);

                    instruction_flag = 0;
                }


            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //undo From GameEnvironment
            }
        });

        textview_statistics.setMovementMethod(new ScrollingMovementMethod());



        //hide status bar
        system_ui_manager = new System_UI_Manager(getActivity());






        ////////////////

        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());

        builder.setTitle("Confirm Restart");
        builder.setMessage("Are you would like to restart? \n (All Progress will be lost)");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                dialog.dismiss();
                system_ui_manager = new System_UI_Manager(getActivity());
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
                system_ui_manager = new System_UI_Manager(getActivity());
            }
        });

        alert = builder.create();

        ///////////////////////////

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });


        iv_11 = (ImageView) root.findViewById(R.id.iv_11);
        boardList.add(iv_11);
        iv_12 = (ImageView) root.findViewById(R.id.iv_12);
        boardList.add(iv_12);
        iv_13 = (ImageView) root.findViewById(R.id.iv_13);
        boardList.add(iv_13);
        iv_14 = (ImageView) root.findViewById(R.id.iv_14);
        boardList.add(iv_14);
        iv_15 = (ImageView) root.findViewById(R.id.iv_15);
        boardList.add(iv_15);
        iv_16 = (ImageView) root.findViewById(R.id.iv_16);
        boardList.add(iv_16);
        iv_17 = (ImageView) root.findViewById(R.id.iv_17);
        boardList.add(iv_17);

        iv_21 = (ImageView) root.findViewById(R.id.iv_21);
        boardList.add(iv_21);
        iv_22 = (ImageView) root.findViewById(R.id.iv_22);
        boardList.add(iv_22);
        iv_23 = (ImageView) root.findViewById(R.id.iv_23);
        boardList.add(iv_23);
        iv_24 = (ImageView) root.findViewById(R.id.iv_24);
        boardList.add(iv_24);
        iv_25 = (ImageView) root.findViewById(R.id.iv_25);
        boardList.add(iv_25);
        iv_26 = (ImageView) root.findViewById(R.id.iv_26);
        boardList.add(iv_26);
        iv_27 = (ImageView) root.findViewById(R.id.iv_27);
        boardList.add(iv_27);

        iv_31 = (ImageView) root.findViewById(R.id.iv_31);
        boardList.add(iv_31);
        iv_32 = (ImageView) root.findViewById(R.id.iv_32);
        boardList.add(iv_32);
        iv_33 = (ImageView) root.findViewById(R.id.iv_33);
        boardList.add(iv_33);
        iv_34 = (ImageView) root.findViewById(R.id.iv_34);
        boardList.add(iv_34);
        iv_35 = (ImageView) root.findViewById(R.id.iv_35);
        boardList.add(iv_35);
        iv_36 = (ImageView) root.findViewById(R.id.iv_36);
        boardList.add(iv_36);
        iv_37 = (ImageView) root.findViewById(R.id.iv_37);
        boardList.add(iv_37);

        iv_41 = (ImageView) root.findViewById(R.id.iv_41);
        boardList.add(iv_41);
        iv_42 = (ImageView) root.findViewById(R.id.iv_42);
        boardList.add(iv_42);
        iv_43 = (ImageView) root.findViewById(R.id.iv_43);
        boardList.add(iv_43);
        iv_44 = (ImageView) root.findViewById(R.id.iv_44);
        boardList.add(iv_44);
        iv_45 = (ImageView) root.findViewById(R.id.iv_45);
        boardList.add(iv_45);
        iv_46 = (ImageView) root.findViewById(R.id.iv_46);
        boardList.add(iv_46);
        iv_47 = (ImageView) root.findViewById(R.id.iv_47);
        boardList.add(iv_47);

        iv_51 = (ImageView) root.findViewById(R.id.iv_51);
        boardList.add(iv_51);
        iv_52 = (ImageView) root.findViewById(R.id.iv_52);
        boardList.add(iv_52);
        iv_53 = (ImageView) root.findViewById(R.id.iv_53);
        boardList.add(iv_53);
        iv_54 = (ImageView) root.findViewById(R.id.iv_54);
        boardList.add(iv_54);
        iv_55 = (ImageView) root.findViewById(R.id.iv_55);
        boardList.add(iv_55);
        iv_56 = (ImageView) root.findViewById(R.id.iv_56);
        boardList.add(iv_56);
        iv_57 = (ImageView) root.findViewById(R.id.iv_57);
        boardList.add(iv_57);

        iv_61 = (ImageView) root.findViewById(R.id.iv_61);
        boardList.add(iv_61);
        iv_62 = (ImageView) root.findViewById(R.id.iv_62);
        boardList.add(iv_62);
        iv_63 = (ImageView) root.findViewById(R.id.iv_63);
        boardList.add(iv_63);
        iv_64 = (ImageView) root.findViewById(R.id.iv_64);
        boardList.add(iv_64);
        iv_65 = (ImageView) root.findViewById(R.id.iv_65);
        boardList.add(iv_65);
        iv_66 = (ImageView) root.findViewById(R.id.iv_66);
        boardList.add(iv_66);
        iv_67 = (ImageView) root.findViewById(R.id.iv_67);
        boardList.add(iv_67);

        //textview_statistics.setText(Integer.toString(boardList.size()));
        return root;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch(checkedId) {
            case R.id.radioButton:
                Log.d("onCheckChanged", Integer.toString(7));
                moveFlag = 7;
                break;
            case R.id.radioButton2:
                Log.d("onCheckChanged", Integer.toString(6));
                moveFlag = 6;
                break;
            case R.id.radioButton3:
                Log.d("onCheckChanged", Integer.toString(5));
                moveFlag = 5;
                break;
            case R.id.radioButton4:
                Log.d("onCheckChanged", Integer.toString(4));
                moveFlag = 4;
                break;
            case R.id.radioButton5:
                Log.d("onCheckChanged", Integer.toString(3));
                moveFlag = 3;
                break;
            case R.id.radioButton6:
                Log.d("onCheckChanged", Integer.toString(2));
                moveFlag = 2;
                break;
            case R.id.radioButton7:
                Log.d("onCheckChanged", Integer.toString(1));
                moveFlag = 1;
                break;
        }
    }

    public void startGame(){
        /*
        b.displayBoard();
        b.placeMove(3, 1);
        b.displayBoard();

        while(true){
            letOpponentMove();
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
        system_ui_manager = new System_UI_Manager(getActivity());
        super.onResume();
    }
}
