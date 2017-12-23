package com.gmail.rashjohar0007.securenotepad.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.rashjohar0007.securenotepad.NotepadActivity;
import com.gmail.rashjohar0007.securenotepad.R;
import com.gmail.rashjohar0007.securenotepad.basic.Cryptography;
import com.gmail.rashjohar0007.securenotepad.basic.IConstants;
import com.gmail.rashjohar0007.securenotepad.models.Note;
import com.gmail.rashjohar0007.securenotepad.viewholders.NoteViewHolder;

import java.security.GeneralSecurityException;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> implements IConstants{
    private Context context;
    private List<Note> notes;

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_note,parent,false));
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        final Note note=notes.get(position);
        holder.title.setText(note.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                String secure= PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_PREF_SECURE,"");
                Note mrn= null;
                try {
                    mrn = new Note(note.getId(),note.getTitle(), Cryptography.decrypt(secure,note.getData()));
                } catch (GeneralSecurityException e) {

                }
                bundle.putSerializable(KEY_BUNDLE_NOTE,mrn);
                Intent intent=new Intent(context,NotepadActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
