package com.example.bbcnewsreader;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ImageView;
/**
 * This class represents a custom help dialog.
 */
public class DialogHelp {
    private Dialog dialog;
    private ImageView toolbarHelp;
    /**
     * Constructs a DialogHelp object.
     *
     * @param context The context of the dialog.
     * @param res     The resource identifier for the help image.
     */
    public DialogHelp(Context context, Integer res){
        this.dialog = new Dialog(context);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_help);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        connectView();
        toolbarHelp.setImageResource(res);
    }
    private void connectView(){
        this.toolbarHelp = this.dialog.findViewById(R.id.toolbar_help);
    }
    /**
     * Shows the help dialog.
     */
    public void show(){

        this.dialog.show();
    }
    /**
     * Hides the help dialog.
     */
    public void hide(){
        this.dialog.dismiss();
    }
}