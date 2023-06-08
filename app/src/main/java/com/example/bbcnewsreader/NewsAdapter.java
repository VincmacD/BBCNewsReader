package com.example.bbcnewsreader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/**
 * The NewsAdapter class is responsible for populating the ListView with news articles.
 * It extends ArrayAdapter to customize the adapter behavior.
 */
public class NewsAdapter extends ArrayAdapter {

    private static Integer resource = R.layout.news_items;
    private Context context;
    private ArrayList<NewsArticle> newsList;

    /**
     * Constructs a new instance of NewsAdapter.
     * @param context The context of the application.
     * @param newsList The list of news articles to be displayed.
     */
    public NewsAdapter(Context context, ArrayList<NewsArticle> newsList) {
        super(context, resource, newsList);
        this.context = context;
        this.newsList = newsList;
    }
    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * Returns the view for each news article item in the ListView.
     * @param position The position of the item in the list.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent ViewGroup that this view will eventually be attached to.
     * @return The view corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int phraseIndex = position;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_items, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.feed_items);
        textView.setText(newsList.get(position).getTitle());
        return convertView;
    }
}


