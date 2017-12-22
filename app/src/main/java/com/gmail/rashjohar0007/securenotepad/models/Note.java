package com.gmail.rashjohar0007.securenotepad.models;

import com.gmail.rashjohar0007.securenotepad.basic.DatabaseSupport;

import java.io.Serializable;

public class Note implements Serializable{
    private int id=0;
    private String data="";
    private String title="";

    public Note() {
    }

    public Note(int id, String title, String data) {
        this.id = id;
        this.data = data;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void SaveNote(String key,DatabaseSupport databaseSupport) {
        databaseSupport.addNote(key,this);
    }
    public void SaveorModifyNote(String key,DatabaseSupport databaseSupport) {
        databaseSupport.addOrUpdateNote(key,this);
    }
}
