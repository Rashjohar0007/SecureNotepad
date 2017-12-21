package com.gmail.rashjohar0007.securenotepad;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gmail.rashjohar0007.securenotepad.R;
import com.gmail.rashjohar0007.securenotepad.basic.BaseActivity;
import com.gmail.rashjohar0007.securenotepad.basic.DatabaseSupport;
import com.gmail.rashjohar0007.securenotepad.basic.IConstants;
import com.gmail.rashjohar0007.securenotepad.models.Note;

public class NotepadActivity extends BaseActivity {
    private AppCompatEditText note_title;
    private AppCompatEditText textData;

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
                if(!note_title.getText().equals("")) {
                    int id=databaseSupport.getAllNotes().size()+1;
                    Note note=new Note(id,note_title.getText().toString(),textData.getText().toString());
                    note.SaveNote(databaseSupport);
                    Snackbar.make(item.getActionView(),"Note Added.",Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(item.getActionView(),"Please Enter a Title First.",Snackbar.LENGTH_SHORT).show();
                }
                break;
            case  android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }
}
