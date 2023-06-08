package com.example.bbcnewsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

/**
 * The phoneFragmentActivity class represents an activity for displaying a phone fragment.
 */
public class phoneFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_fragment);
        super.initToolBar(getResources().getString(R.string.news_detail) + " " + getResources().getString(R.string.version));
        super.initNavigationDrawer();
        super.setContext(this);
        if (savedInstanceState == null) {
            NewsDetailFragment fragment = new NewsDetailFragment(this);//starts a new fragment activity
            fragment.setArguments(getIntent().getExtras());//collects data to pass over
            getSupportFragmentManager().beginTransaction()//begins transaction
                    .replace(R.id.fragmentLocation, fragment)
                    .commit();
        }
    }
}