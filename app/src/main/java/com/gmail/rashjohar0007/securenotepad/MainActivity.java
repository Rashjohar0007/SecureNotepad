package com.gmail.rashjohar0007.securenotepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gmail.rashjohar0007.securenotepad.adapters.NoteAdapter;
import com.gmail.rashjohar0007.securenotepad.basic.BaseActivity;
import com.gmail.rashjohar0007.securenotepad.basic.Constants;
import com.gmail.rashjohar0007.securenotepad.models.Note;

import java.util.List;

public class MainActivity extends BaseActivity{
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshview;
    private List<Note> notes;
    private NoteAdapter adapter;
    private AppCompatEditText editText;
    private AppCompatEditText editText_sec;
    private String loginkey;
    private String securekey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editText=new AppCompatEditText(this);
        editText_sec=new AppCompatEditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT));
        editText_sec.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT));
        if(!preferences.contains(KEY_PREF_LOGIN)) {
            Constants.AskLoginPassword(this, "Choose Login Password", editText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface ddm, int i) {
                    loginkey=editText.getText().toString();

                    if(!preferences.contains(KEY_PREF_SECURE)) {
                        Constants.AskLoginPassword(MainActivity.this, "Choose Notepad Secure Password", editText_sec, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                securekey=editText_sec.getText().toString();
                                databaseSupport.addUSERLogin(loginkey,securekey);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString(KEY_PREF_LOGIN,loginkey);
                                editor.putString(KEY_PREF_SECURE,securekey);
                                editor.commit();
                                dialogInterface.dismiss();
                                ddm.dismiss();
                                recreate();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).show();
                    }
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).show();
        }else {
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            refreshview = (SwipeRefreshLayout) findViewById(R.id.refreshview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            notes=databaseSupport.getAllNotes();
            adapter=new NoteAdapter(this,notes);
            recyclerView.setAdapter(adapter);
            refreshview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    notes=databaseSupport.getAllNotes();
                    adapter.notifyDataSetChanged();
                    refreshview.setRefreshing(false);
                }
            });
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Note note=new Note(databaseSupport.getKey(),"","");
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(KEY_BUNDLE_NOTE,note);
                    startActivity(NotepadActivity.class,bundle,START_ACTIVITY_FOR_NO_RESULT);
                }
            });
        }
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
