package com.example.bbcnewsreader;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.util.Locale;
import java.util.ArrayList;

/**
 * The MainActivity class represents the main activity of the application.
 * It extends the BaseActivity class and provides functionalities for displaying a list of news articles,
 * searching within the articles, and navigating to detailed article views.
 */
public class MainActivity extends BaseActivity {
    private EditText editText;
    private ImageButton btn_search;
    private ImageButton btn_cancel;
    private ListView newsListView;
    private ArrayList<NewsArticle> news;
    private ArrayList<NewsArticle> data = new ArrayList();
    private ProgressBar progressBar;
    private NewsAdapter newsAdapter;
    private FrameLayout fragmentLocation;
    private NewsArticle dataItem;
    public static String addFav, delFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.fragment_news_detail);
        super.initToolBar(getResources().getString(R.string.nav_home) + " " + getResources().getString(R.string.version));
        super.initNavigationDrawer();
        super.setContext(this);
        MyOpener dbOpener = new MyOpener(getApplicationContext());

        initView();
        initData();
        ButtonAction();
        ListOnClickAction();

    }
    public void initView() {
        // Initializes the views and adapters used in the activity.
        editText = findViewById(R.id.editText_search);
        btn_search = findViewById(R.id.btn_search);
        btn_cancel = findViewById(R.id.btn_cancel);
        progressBar = findViewById(R.id.progress_horizontal);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(242,57,109)));
        newsListView = findViewById(R.id.news_title_listView);
        newsAdapter = new NewsAdapter(this, data);
        newsListView.setAdapter(newsAdapter);
        fragmentLocation = findViewById(R.id.fragmentLocation);
        addFav = getResources().getString(R.string.add_toast);
        delFav = getResources().getString(R.string.delete_toast);
    }
    public void ListOnClickAction(){
        //whenever a user clicks an item on the favorites list
        newsListView.setOnItemClickListener((parent, view, position, id) ->{
            /**
             * MainActivity and FavoritesActivity uses the same fragment ( NewsDetailFragment ) to
             * display their respective list. the ActivityId variable is to determine which Activity
             * is calling the fragment. MainActivity will show a ADD button for the favorites List.
             * FavoritesActivity will show a REMOVE button for the favorites List.
             * The fragmentId variable is to determine how the fragment will close in NewsDetailFragment
             * depending if user is using a phone or tablet
             */
            if(fragmentLocation == null) {//if user is using a phone

            dataItem = (NewsArticle) parent.getItemAtPosition(position);//gets item data it's clicked position
            Intent intent = new Intent(MainActivity.this, phoneFragmentActivity.class);// intent to enter the phoneFragmentActivity
            Bundle bundle = new Bundle(); //creating bundle to pass data over to new activity
            bundle.putInt("ActivityId", 1);//fragment called from MainActivity
                //collects item data to pass over
            bundle.putString(String.valueOf(R.id.news_title_data), String.valueOf((dataItem.getTitle())));
            bundle.putString(String.valueOf(R.id.news_description_data), String.valueOf((dataItem.getDescription())));
            bundle.putString(String.valueOf(R.id.news_date_data), String.valueOf((dataItem.getDate())));
            bundle.putString(String.valueOf(R.id.news_URL_data), String.valueOf((dataItem.getHTTPLink())));
            intent.putExtras(bundle); // sets bundle
            startActivity(intent); //starts new activity
            }else{// if user is using a tablet
                dataItem = (NewsArticle) parent.getItemAtPosition(position);
                NewsDetailFragment fragment = new NewsDetailFragment(this);
                Bundle args = new Bundle();
                args.putInt("ActivityId", 1);//fragment called from MainActivity
                args.putString(String.valueOf(R.id.news_title_data), String.valueOf((dataItem.getTitle()))); //collects item data to pass over
                args.putString(String.valueOf(R.id.news_description_data), String.valueOf((dataItem.getDescription())));
                args.putString(String.valueOf(R.id.news_date_data), String.valueOf((dataItem.getDate())));
                args.putString(String.valueOf(R.id.news_URL_data), String.valueOf((dataItem.getHTTPLink())));
                fragment.setArguments(args);// sets bundle
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, fragment).commit();//starts fragment transaction
            }
        });
    }
    private void initData(){
        //initializes the news feed from the BBC website to then pass into the data list
        try {
            news = SessionUtilities.get(this, SessionUtilities.FEED, null);
            if(news.size() == 0){
                news.addAll(new MyAsyncTask().execute("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml").get());
                SessionUtilities.set(this, SessionUtilities.FEED, news);
            }
            data.addAll(news);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void ButtonAction(){ // method for the filter search and clear button on the editText
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //collects text from editText
                String search_text = editText.getText().toString();
                data.clear();
                for(int i =0; i < news.size(); i++){
                    Integer progress = (Integer) (i+1)*100/news.size();
                    progressBar.setProgress(progress);
                    //gets items from news which contains the text
                    if(news.get(i).getTitle().toLowerCase(Locale.ROOT).contains(search_text.toLowerCase(Locale.ROOT))){
                        data.add(news.get(i)); // adds searched items from news to data list
                    }
                    newsAdapter.notifyDataSetChanged();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.clear();//clears the current data list
                data.addAll(news);//add all items from news list
                String cancel_text = ""; // resets editText
                progressBar.setProgress(0); //resets progress bar
                editText.setText(cancel_text);
                newsAdapter.notifyDataSetChanged();
            }
        });
    }
}