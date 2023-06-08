package com.example.bbcnewsreader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

/**
 * The NewsDetailFragment class represents a fragment for displaying detailed information about a news article.
 */
public class NewsDetailFragment extends Fragment {
    private View view;
    private TextView titleDataTV;
    private TextView descDataTV;
    private TextView dateDataTV;
    private TextView URLDataTV;
    private Button btn_add_favorite, btn_delete_favorite;
    private Bundle args;
    private String fillMe_title;
    private String fillMe_desc;
    private String fillMe_date;
    private String fillMe_URL;
    int activityId;

    int fragmentId;
    int favItemLocation;

    String favItemId;

    Context context;
    ConstraintLayout fragmentLocation;

    public NewsDetailFragment(){
    }
    /**
     * Constructs a NewsDetailFragment object with the specified context.
     * @param context The context associated with the fragment.
     */
    public NewsDetailFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details, container, false);
        initData();
        buttonAction();

        return view;
    }
    private void initData() {
        // Inflate the layout for this fragment
        titleDataTV = view.findViewById(R.id.news_title_data);
        descDataTV = view.findViewById(R.id.news_description_data);
        dateDataTV = view.findViewById(R.id.news_date_data);
        URLDataTV = view.findViewById(R.id.news_URL_data);
        fragmentLocation = view.findViewById(R.id.fragmentLocation);

        args = getArguments();
        if (args != null) {
            activityId = args.getInt(String.valueOf("ActivityId"));//needed to show ADD or REMOVE option
            fragmentId = args.getInt(String.valueOf("fragmentId"));//needed to tell app how to exit fragment
            favItemLocation = args.getInt(String.valueOf("favItemPosition"));//needed to add or delete selected item from list
           //collects information about item
            favItemId = String.valueOf(args.getLong(String.valueOf("favItemId")));
            fillMe_title = args.getString(String.valueOf(R.id.news_title_data));
            titleDataTV.setText(fillMe_title);
            fillMe_desc = args.getString(String.valueOf(R.id.news_description_data));
            descDataTV.setText((fillMe_desc));
            fillMe_date = args.getString(String.valueOf(R.id.news_date_data));
            dateDataTV.setText((fillMe_date));
            fillMe_URL = args.getString(String.valueOf(R.id.news_URL_data));
            URLDataTV.setText(Html.fromHtml(String.format("<u>%s<u>", fillMe_URL)));
            URLDataTV.setTextColor(getResources().getColor(R.color.urlBlue));
        }
        if(activityId == 1){//shows add button
            btn_add_favorite = view.findViewById(R.id.btn_add_favorites);
            btn_add_favorite.setVisibility(view.VISIBLE);
        } else if (activityId == 2) {//shows remove button
            btn_delete_favorite = view.findViewById(R.id.btn_delete_favorites);
            btn_delete_favorite.setVisibility(view.VISIBLE);
        }
    }
    private void buttonAction() {
        URLDataTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//makes the URL clickable and sends user to webpage
                String URL = URLDataTV.getText().toString();
                intentBrowser(URL);
            }
        });
        if (activityId == 1) {//action when user adds article to favorites list
            btn_add_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadContentInFav();
                }
            });
        } else if (activityId == 2) {//action when user removes article from favorites list
            btn_delete_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//snack bar to confirm if user wants to delete article
                    Snackbar.make(fragmentLocation, R.string.delete_confirmation, Snackbar.LENGTH_LONG)
                                    .setAction(R.string.delete_snackbar, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteContentInFav();
                                        }
                                    })
                            .show();
                }
            });
        }
    }
    private void loadContentInFav() {//action paired with the ADD button to add item to db
        DataOperationAsyncTask addDataAT = new DataOperationAsyncTask(context);
        addDataAT.execute("add_info", fillMe_title, fillMe_desc, fillMe_date, fillMe_URL);
    }
    private void DeleteContentInFav() {//action paired with the REMOVE button to remove item from db
        DataOperationAsyncTask addDataAT = new DataOperationAsyncTask(context);
        addDataAT.execute("delete_info", favItemId);
        FavoritesActivity.favoritesList.remove(favItemLocation);//remove item from favorite list view
        FavoritesActivity.newsAdapter.notifyDataSetChanged();//notify view
        if (fragmentId == 1) {//once removed, phone user gets brought back to phoneFragmentActivity
            getActivity().onBackPressed();
        } else if (fragmentId == 2) {//once removed, tablet user ends the fragment displayed on screen
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

        }
    }
    private void intentBrowser(String URL) {//action to bring user to webpage when clicking an URL
        Intent browser = new Intent(Intent.ACTION_VIEW);
        browser.setData(Uri.parse(URL));
        startActivity(browser);
    }
}