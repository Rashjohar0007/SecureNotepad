package com.gmail.rashjohar0007.securenotepad.basic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements IConstants{
    protected DatabaseSupport databaseSupport;
    protected SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        databaseSupport=DatabaseSupport.getInstance(this);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
    }
    public void startActivity(Class activity, Bundle bundle, int requestCode) {
        Intent intent=new Intent(getApplicationContext(),activity);
        intent.putExtras(bundle);
        if(requestCode==START_ACTIVITY_FOR_NO_RESULT) {
            startActivity(intent);
        } else {
            startActivityForResult(intent,requestCode);
        }

    }
}
