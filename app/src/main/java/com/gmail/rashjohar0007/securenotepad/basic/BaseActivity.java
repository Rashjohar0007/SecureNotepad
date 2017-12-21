package com.gmail.rashjohar0007.securenotepad.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gmail.rashjohar0007.securenotepad.NotepadActivity;
import com.gmail.rashjohar0007.securenotepad.R;

public class BaseActivity extends AppCompatActivity implements IConstants{
    protected DatabaseSupport databaseSupport;
    public void startActivity(Class activity, Bundle bundle, int requestCode) {
        Intent intent=new Intent(getApplicationContext(),activity);
        intent.putExtras(bundle);
        databaseSupport=DatabaseSupport.getInstance(this);
        if(requestCode==START_ACTIVITY_FOR_NO_RESULT) {
            startActivity(intent);
        } else {
            startActivityForResult(intent,requestCode);
        }

    }
}
