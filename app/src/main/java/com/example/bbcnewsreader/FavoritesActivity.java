package com.example.bbcnewsreader;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.Locale;
/**
 * The FavoritesActivity class represents the activity that displays the user's favorite news articles.
 * It extends the BaseActivity class and provides functionalities for displaying a list of favorite articles,
 * searching within the favorites, and navigating to detailed article views.
 */
public class FavoritesActivity extends BaseActivity {
    private ListView favListView;
    public static NewsAdapter newsAdapter;
    private FrameLayout fragmentLocation;
    private NewsArticle favItem;
    private EditText editText;
    private ImageButton btn_search;
    private ImageButton btn_cancel;
    private ProgressBar progressBar;
    public static ArrayList<NewsArticle> news = new ArrayList();
    private Integer progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorites);
        super.initToolBar(getResources().getString(R.string.nav_favorites) + " " + getResources().getString(R.string.version));
        super.initNavigationDrawer();
        super.setContext(this);
        initView();
        super.loadDataFromDatabase();
        ListOnClickAction();
        ButtonAction();
    }
    @Override
    protected void onResume(){
        //loads database for when user exits NewsDetailFragment from a phone
        super.onResume();
        super.loadDataFromDatabase();
    }

    public void initView() {
        // Initializes the views and adapters used in the activity.
        favListView = findViewById(R.id.favorites_title_listView);
        newsAdapter = new NewsAdapter(this, BaseActivity.favoritesList);
        favListView.setAdapter(newsAdapter);
        fragmentLocation = findViewById(R.id.fragmentLocation);
        editText = findViewById(R.id.editText_search);
        btn_search = findViewById(R.id.btn_search);
        btn_cancel = findViewById(R.id.btn_cancel);
        progressBar = findViewById(R.id.progress_horizontal);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(242,57,109)));
    }

    public void ListOnClickAction(){
        //whenever a user clicks an item on the favorites list
        favListView.setOnItemClickListener((parent, view, position, id) ->{
            /**
            * MainActivity and FavoritesActivity uses the same fragment ( NewsDetailFragment ) to
            * display their respective list. the ActivityId variable is to determine which Activity
             * is calling the fragment. MainActivity will show a ADD button for the favorites List.
             * FavoritesActivity will show a REMOVE button for the favorites List.
             * The fragmentId variable is to determine how the fragment will close in NewsDetailFragment
             * depending if user is using a phone or tablet
            */
            if(fragmentLocation == null) {  //if user is using a phone
                favItem = (NewsArticle) parent.getItemAtPosition(position);//gets item data it's clicked position
                // intent to enter the phoneFragmentActivity
                Intent intent = new Intent(FavoritesActivity.this, phoneFragmentActivity.class);
                //creating bundle to pass data over to new activity
                Bundle bundle = new Bundle();
                bundle.putInt("ActivityId", 2); //fragment called from FavoritesActivity
                bundle.putLong("favItemId", favItem.getId());
                bundle.putInt("fragmentId", 1); //if user is using a phone
                bundle.putInt("favItemPosition", position); //passes item position in list
                //collects item data to pass over
                bundle.putString(String.valueOf(R.id.news_title_data), String.valueOf((favItem.getTitle())));
                bundle.putString(String.valueOf(R.id.news_description_data), String.valueOf((favItem.getDescription())));
                bundle.putString(String.valueOf(R.id.news_date_data), String.valueOf((favItem.getDate())));
                bundle.putString(String.valueOf(R.id.news_URL_data), String.valueOf((favItem.getHTTPLink())));
                intent.putExtras(bundle); // sets bundle
                startActivity(intent); //starts new activity
            }else{  // if user is using a tablet
                favItem = (NewsArticle) parent.getItemAtPosition(position);
                NewsDetailFragment fragment = new NewsDetailFragment(this);
                Bundle args = new Bundle();
                args.putInt("ActivityId", 2); //fragment called from FavoritesActivity
                args.putLong("favItemId", favItem.getId());
                args.putInt("fragmentId", 2); // if user is using a tablet
                args.putInt("favItemPosition", position); // passes item position in list
                //collects item data to pass over
                args.putString(String.valueOf(R.id.news_title_data), String.valueOf((favItem.getTitle())));
                args.putString(String.valueOf(R.id.news_description_data), String.valueOf((favItem.getDescription())));
                args.putString(String.valueOf(R.id.news_date_data), String.valueOf((favItem.getDate())));
                args.putString(String.valueOf(R.id.news_URL_data), String.valueOf((favItem.getHTTPLink())));
                fragment.setArguments(args); // sets bundle
                //starts fragment transaction
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, fragment).commit();
            }
        });
    }
    private void ButtonAction(){ // method for the filter search and clear button on the editText
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progress == null) {
                    String search_text = editText.getText().toString();//collects text from editText
                    news.addAll(BaseActivity.favoritesList); //adds all of the a list item to a news list
                    BaseActivity.favoritesList.clear(); // clears the favorites list
                    for (int i = 0; i < news.size(); i++) { // iterates through the news list
                        progress = (Integer) (i + 1) * 100 / news.size();
                        progressBar.setProgress(progress);  // starts the progress bar
                        //gets items from news which contains the text
                        if (news.get(i).getTitle().toLowerCase(Locale.ROOT).contains(search_text.toLowerCase(Locale.ROOT))) {
                            BaseActivity.favoritesList.add(news.get(i)); // adds searched items from news to favlist
                        }
                        newsAdapter.notifyDataSetChanged(); //notifies the view of the change
                    }
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                news.clear(); //clears all items from new list
                progressBar.setProgress(0);//resets progress bar
                progress = null;
                String cancel_text = ""; // resets editText
                editText.setText(cancel_text);
                newsAdapter.notifyDataSetChanged();
                loadDataFromDatabase();
            }
        });
    }
}
