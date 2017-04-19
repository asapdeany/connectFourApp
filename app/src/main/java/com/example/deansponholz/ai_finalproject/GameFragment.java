package com.example.deansponholz.ai_finalproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Service;
import android.content.DialogInterface;
import android.os.Bundle;
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

    @Override
    public void onResume() {
        system_ui_manager = new System_UI_Manager(getActivity());
        super.onResume();
    }
}
