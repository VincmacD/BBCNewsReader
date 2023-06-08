package com.example.bbcnewsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

/**
 * This activity allows the user to change the language of the application between french and english.
 */
public class ChangeLanguageActivity extends BaseActivity{

    Button engButton, freButton;
    Context context;
    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        super.initToolBar(getResources().getString(R.string.language_activity) + " " + getResources().getString(R.string.version));
        super.initNavigationDrawer();
        super.setContext(this);

        initView();
        buttonAction();
    }
    public void initView() {
        // Initializes the views and adapters used in the activity.
        engButton = findViewById(R.id.btnEnglish);
        freButton = findViewById(R.id.btnFrench);
    }
    private void buttonAction(){
        engButton.setOnClickListener(new View.OnClickListener() { //if user wants english
            @Override
            public void onClick(View v) {
                LocaleHelper newLH = new LocaleHelper();
                //activates the methods in LocaleHelper
                newLH.setNewLocale(ChangeLanguageActivity.this, Locale.getDefault().getLanguage());
                restartActivity();
            }
        });
        freButton.setOnClickListener(new View.OnClickListener() { //if user wants french
            @Override
            public void onClick(View v) {
                LocaleHelper newLH = new LocaleHelper();
                //activates the methods in LocaleHelper
                newLH.setNewLocale(ChangeLanguageActivity.this, "fr");
                restartActivity();
            }
        });
    }
    /**
     * Restarts the activity to apply the new language settings.
     */
    private void restartActivity(){
        intent = getIntent();
        finish();
        startActivity(intent);
    }
}