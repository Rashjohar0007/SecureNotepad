package com.gmail.rashjohar0007.securenotepad.viewholders;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gmail.rashjohar0007.securenotepad.R;

public class NoteViewHolder extends RecyclerView.ViewHolder{
    public CardView cardView;
    public AppCompatTextView title;

    public NoteViewHolder(View itemView) {
        super(itemView);
        cardView=(CardView) itemView.findViewById(R.id.cardview);
        title=(AppCompatTextView) itemView.findViewById(R.id.title);
    }
}
