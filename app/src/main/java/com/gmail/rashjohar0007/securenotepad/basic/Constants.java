package com.gmail.rashjohar0007.securenotepad.basic;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class Constants implements IConstants{
    public static AlertDialog AskLoginPassword(Context context, String title,View v, DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(v);
        builder.setCancelable(false);
        builder.setPositiveButton("Save",positive);
        builder.setNegativeButton("Cancle",negative);
        return builder.create();
    }
}
