package com.example.deansponholz.ai_finalproject;

import android.app.Service;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by deansponholz on 4/8/17.
 */

public class GameActivity extends AppCompatActivity {

    //TextView gameboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);



        //gameboard = (TextView) findViewById(R.id.gameBoard_tv);

        //GameEnvironment gameEnvironment = new GameEnvironment();
        //gameEnvironment.displayEnvironment(gameboard);

    }
}
