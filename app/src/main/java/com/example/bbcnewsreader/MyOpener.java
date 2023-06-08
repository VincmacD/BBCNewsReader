package com.example.bbcnewsreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The MyOpener class is responsible for creating and managing the SQLite database used for storing or removing favorite news articles.
 * It extends SQLiteOpenHelper to handle database creation and version management.
 */
public class MyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME="favDB";

    protected final static int VERSION_NUM = 1;

    public final static String TABLE_NAME = "FAVORITES";

    public final static String COL_TITLE = "TITLE";

    public final static String COL_DESC = "DESCRIPTION";

    public final static String COL_DATE = "DATE";

    public final static String COL_URL = "URL";

    public final static String COL_ID = "ID";

    /**
     * Constructs a new instance of MyOpener.
     * @param context The context of the application.
     */
    public MyOpener(Context context) {
        super(context, DATABASE_NAME,null,VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TITLE + " text,"
                + COL_DESC + " text,"
                + COL_DATE + " text,"
                + COL_URL + " text);");
    }
    /**
     * Inserts a new favorite news article into the database.
     * @param db The SQLiteDatabase instance.
     * @param title The title of the news article.
     * @param desc The description of the news article.
     * @param date The date of the news article.
     * @param URL The URL of the news article.
     */
    public void addInformation(SQLiteDatabase db, String title, String desc, String date, String URL){
        ContentValues newRowValues = new ContentValues(); //creation of new db row
        newRowValues.put(COL_TITLE, title);
        newRowValues.put(COL_DESC, desc);
        newRowValues.put(COL_DATE, date);
        newRowValues.put(COL_URL,URL);
        long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues); //inserts new id and item
        NewsArticle newFavorite = new NewsArticle(title, desc, date, URL, newId); //creates NewsArticle object
        BaseActivity.favoritesList.add(newFavorite);//add NewsArticle object into favorites list
    }

    /**
     * Deletes a favorite news article from the database.
     * @param db The SQLiteDatabase instance.
     * @param id The ID of the news article to be deleted.
     */
    public void deleteInformation(SQLiteDatabase db, String id){
        db.delete(MyOpener.TABLE_NAME,  MyOpener.COL_ID + "= ? ", new String[]{
                id});

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
