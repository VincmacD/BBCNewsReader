package com.example.bbcnewsreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

/**
 * This is a base activity class that other activities in the app can extend.
 * It provides common functionality for setting up the toolbar and navigation drawer.
 */
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BaseActivity context;
    private Toolbar toolBar;
    private String title;
    private SQLiteDatabase db;
    public static ArrayList<NewsArticle> favoritesList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    public void setContext(BaseActivity context) {
        this.context = context;
    }

    public void initToolBar(String titlePage){
        // Initializing the tool bar
        this.title = titlePage;
        toolBar = findViewById(R.id.toolbar);
        toolBar.setTitle(titlePage);
        setSupportActionBar(toolBar);
    }
    public void initNavigationDrawer(){
        // Initializing the tool bar
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.navbar);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // action taken when clicking on navigation options
        int itemId = item.getItemId();
        if (itemId == R.id.navbar_home) {
            if (!(this.context instanceof MainActivity))
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if (itemId == R.id.navbar_favorites) {
            if (!(this.context instanceof FavoritesActivity))
                startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
        } else if (itemId == R.id.navbar_addArticle) {
            if (!(this.context instanceof AddArticleActivity))
                startActivity(new Intent(getApplicationContext(), AddArticleActivity.class));
        }else if (itemId == R.id.navbar_changeLanguage) {
            if (!(this.context instanceof ChangeLanguageActivity))
                startActivity(new Intent(getApplicationContext(), ChangeLanguageActivity.class));
        }else if (itemId == R.id.navbar_help){
            Integer res = R.drawable.details_page;
            if(this.context instanceof FavoritesActivity) res = R.drawable.favorites_page;
            else if(this.context instanceof  AddArticleActivity) res = R.drawable.add_page;
            else if(this.context instanceof  ChangeLanguageActivity) res = R.drawable.language_page;
            else if(this.context instanceof MainActivity) res = R.drawable.home_page;
            new DialogHelp(this, res).show();
        }else if (itemId == R.id.navbar_exit) {
            finishAffinity();
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // action taken when clicking on toolbar options
        int itemId = item.getItemId();
        if (itemId == R.id.toolbar_home) {
            if (!(this.context instanceof MainActivity))
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if (itemId == R.id.toolbar_favorites) {
            if (!(this.context instanceof FavoritesActivity))
                startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
        } else if (itemId == R.id.toolbar_addArticle) {
            if (!(this.context instanceof AddArticleActivity))
                startActivity(new Intent(getApplicationContext(), AddArticleActivity.class));
        }else if (itemId == R.id.toolbar_changeLanguage) {
            if (!(this.context instanceof ChangeLanguageActivity))
                startActivity(new Intent(getApplicationContext(), ChangeLanguageActivity.class));
        }else if (itemId == R.id.toolbar_help){
            Integer res = R.drawable.details_page;
            if(this.context instanceof FavoritesActivity) res = R.drawable.favorites_page;
            else if(this.context instanceof  AddArticleActivity) res = R.drawable.add_page;
            else if(this.context instanceof  ChangeLanguageActivity) res = R.drawable.language_page;
            else if(this.context instanceof MainActivity) res = R.drawable.home_page;
            new DialogHelp(this, res).show();
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflates toolbar menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    public void loadDataFromDatabase(){
        //loads data from database and populates favorites list
        favoritesList.clear(); // prevents duplicate data on list
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();
        String[] columns = {
                MyOpener.COL_ID,
                MyOpener.COL_TITLE,
                MyOpener.COL_DESC,
                MyOpener.COL_DATE,
                MyOpener.COL_URL
        };
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        //collecting data from database favDB columns
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);
        int titleColIndex = results.getColumnIndex(MyOpener.COL_TITLE);
        int descColIndex = results.getColumnIndex(MyOpener.COL_DESC);
        int dateColIndex = results.getColumnIndex(MyOpener.COL_DATE);
        int URLColIndex = results.getColumnIndex(MyOpener.COL_URL);

        //adds data to create and add new items to the favorites list
        while(results.moveToNext()) {
            long id = results.getLong(idColIndex);
            String title = results.getString(titleColIndex);
            String desc = results.getString(descColIndex);
            String date = results.getString(dateColIndex);
            String URL = results.getString(URLColIndex);
            BaseActivity.favoritesList.add(new NewsArticle(title, desc, date, URL, id));
        }
    }
}