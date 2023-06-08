package com.example.bbcnewsreader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * This class performs data operations asynchronously, such as adding or deleting information from the database.
 */
public class DataOperationAsyncTask extends AsyncTask<String, Void, String> {
    Context context;
    DataOperationAsyncTask(Context context){
        this.context = context;
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... params){
        //collects string of an if statement in NewsDetailFragment to add or delete item from db
        String method = params[0];
        MyOpener dbOpener = new MyOpener(context);
        if(method.equals("add_info")){
        //collects item data from strings
            String title = params[1];
            String desc = params[2];
            String date = params[3];
            String URL = params[4];
            SQLiteDatabase db = dbOpener.getWritableDatabase();
            //adds item data to make a new row in db
            dbOpener.addInformation(db, title, desc, date, URL);
            //returns a string to declare item has been added to db
            return (MainActivity.addFav);
        } else if (method.equals("delete_info")) {
            String id = params[1]; //collects db id of item
            SQLiteDatabase db = dbOpener.getWritableDatabase();
            dbOpener.deleteInformation(db, id); //deletes item
            return (MainActivity.delFav); // returns string to declare item has been deleted from db
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void...values){
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String result){
        //shows a toast to show whether item has been added or removed from favDB db
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

}
