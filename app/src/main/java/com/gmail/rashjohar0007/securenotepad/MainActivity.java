package com.gmail.rashjohar0007.securenotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gmail.rashjohar0007.securenotepad.adapters.NoteAdapter;
import com.gmail.rashjohar0007.securenotepad.basic.BaseActivity;
import com.gmail.rashjohar0007.securenotepad.models.Note;

import java.util.List;

public class MainActivity extends BaseActivity{
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshview;
    private List<Note> notes;
    private NoteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
