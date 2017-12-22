package com.gmail.rashjohar0007.securenotepad.basic;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class BaseApplication extends Application implements IConstants{
    protected SharedPreferences preferences;
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
