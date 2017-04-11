package com.example.deansponholz.ai_finalproject;

import android.app.Fragment;
import android.app.Service;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.security.Provider;

/**
 * Created by deansponholz on 4/8/17.
 */

public class GameFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{


    private RadioGroup radioGroup;
    private System_UI_Manager system_ui_manager;
    static int moveFlag = -1;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gameplay, container, false);

        radioGroup = (RadioGroup) root.findViewById(R.id.radioGroup_gameplay);
        radioGroup.setOnCheckedChangeListener(this);

        //hide status bar
        system_ui_manager = new System_UI_Manager(getActivity());



        return root;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch(checkedId) {
            case R.id.radioButton:
                Log.d("onCheckChanged", Integer.toString(1));
                moveFlag = 1;
                break;
            case R.id.radioButton2:
                Log.d("onCheckChanged", Integer.toString(2));
                moveFlag = 2;
                break;
            case R.id.radioButton3:
                Log.d("onCheckChanged", Integer.toString(3));
                moveFlag = 3;
                break;
            case R.id.radioButton4:
                Log.d("onCheckChanged", Integer.toString(4));
                moveFlag = 4;
                break;
            case R.id.radioButton5:
                Log.d("onCheckChanged", Integer.toString(5));
                moveFlag = 5;
                break;
            case R.id.radioButton6:
                Log.d("onCheckChanged", Integer.toString(6));
                moveFlag = 6;
                break;
            case R.id.radioButton7:
                Log.d("onCheckChanged", Integer.toString(7));
                moveFlag = 7;
                break;
        }
    }
}
