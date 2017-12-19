package com.gmail.rashjohar0007.securenotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gmail.rashjohar0007.securenotepad.basic.BaseActivity;
import com.gmail.rashjohar0007.securenotepad.basic.IConstants;

public class MainActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(NotepadActivity.class,new Bundle(),START_ACTIVITY_FOR_NOTEPAD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if(resultCode==START_ACTIVITY_FOR_NOTEPAD) {

                }
                break;
            case RESULT_CANCELED:
                break;
        }
    }
}
