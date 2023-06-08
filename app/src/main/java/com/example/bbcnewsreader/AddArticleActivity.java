package com.example.bbcnewsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * This activity allows the user to add an article that is not on the news feed.
 */
public class AddArticleActivity extends BaseActivity {

    EditText editTitle, editDesc, editDate, editURL;
    Button addButton;
    String title, desc, date, URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        super.initToolBar(getResources().getString(R.string.add_activity) + " " + getResources().getString(R.string.version));
        super.initNavigationDrawer();
        super.setContext(this);
        initView();
        buttonAction();
    }
    /**
     * Initializes the views and adapters used in the activity.
     */
    public void initView() {
        // Initializes the views and adapters used in the activity.
    editTitle = findViewById(R.id.news_title_data);
    editDesc = findViewById(R.id.news_description_data);
    editDate = findViewById(R.id.news_date_data);
    editURL = findViewById(R.id.news_URL_data);
    addButton = findViewById(R.id.btn_add_favorites);
    }
    /**
     * Collects data from the input fields.
     */
    public void collectData(){
        title = editTitle.getText().toString();
        desc = editDesc.getText().toString();
        date = editDate.getText().toString();
        URL = editURL.getText().toString();
    }
    /**
     * Resets the input fields.
     */
    public void resetEditText(){
        editTitle.setText("");
        editDesc.setText("");
        editDate.setText("");
        editURL.setText("");
    }
    private void buttonAction(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectData();
                loadContentInFav();
                resetEditText();
            }
        });
    }
    /**
     * Loads the content in the favorites.
     */
    private void loadContentInFav() {//action paired with the ADD button to add item to db
        DataOperationAsyncTask addDataAT = new DataOperationAsyncTask(this);
        addDataAT.execute("add_info", title, desc, date, URL);
    }
}