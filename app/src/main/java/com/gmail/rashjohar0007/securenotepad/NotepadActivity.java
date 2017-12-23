package com.gmail.rashjohar0007.securenotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.rashjohar0007.securenotepad.basic.BaseActivity;
import com.gmail.rashjohar0007.securenotepad.basic.Cryptography;
import com.gmail.rashjohar0007.securenotepad.models.Note;

import java.security.GeneralSecurityException;

public class NotepadActivity extends BaseActivity {
    private AppCompatEditText note_title;
    private AppCompatEditText textData;
    private Intent intent;
    private Bundle bundle;
    private Note thisnote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        note_title=(AppCompatEditText) findViewById(R.id.note_title);
        textData=(AppCompatEditText) findViewById(R.id.textData);
        intent=getIntent();
        bundle=getIntent().getExtras();
        if(bundle.containsKey(KEY_BUNDLE_NOTE)) {
            thisnote= (Note) bundle.get(KEY_BUNDLE_NOTE);
        }
        note_title.setText(thisnote.getTitle());
        textData.setText(thisnote.getData());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_act_notepad,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_save:
                if(!note_title.getText().toString().equals("")) {
                    if(preferences.contains(KEY_PREF_SECURE)) {
                        String secure=preferences.getString(KEY_PREF_SECURE,"");
                        String login=preferences.getString(KEY_PREF_LOGIN,"");
                        int id=thisnote.getId();
                        String data= "";
                        String key="";
                        try {
                            data = Cryptography.encrypt(secure,textData.getText().toString());
                            key= Cryptography.encrypt(login,secure);
                        } catch (GeneralSecurityException e) {

                        }

                        /*SecretKey pass= null;
                        try {
                            pass = Cryptography.generateKey(secure);
                        } catch (Exception e) {

                        }
                        String key=Cryptography.ConvertKeyToString(pass);
                        String data= "";
                        try {
                            data = Cryptography.Encrypt(pass,textData.getText().toString());
                        } catch (Exception e) {
                            Log.e(TAG,e.getMessage());
                        }*/
                        Note note=new Note(id,note_title.getText().toString(),data);
                        note.SaveorModifyNote(key,databaseSupport);
                        Toast.makeText(getApplicationContext(),"Note Added.",Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(MainActivity.class,new Bundle(),START_ACTIVITY_FOR_NO_RESULT);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Please Enter a Title First.",Toast.LENGTH_SHORT).show();
                }
                break;
            case  android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }
}
