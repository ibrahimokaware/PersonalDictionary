package com.rivierasoft.personaldictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.rivierasoft.personaldictionary.model.Word;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "words_db";
    public static final int DB_VERSION = 1;

    public static final String WORDS_TB_NAME = "words";
    public static final String WORDS_CLN_ID = "id";
    public static final String WORDS_CLN_TEXT = "text";
    public static final String WORDS_CLN_NOTE = "note";
    public static final String WORDS_CLN_IS_FAVORITE = "is_favorite";
    public static final String WORDS_CLN_COLOR = "color";

    public MyDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+WORDS_TB_NAME+" ("+WORDS_CLN_ID+" INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, " + WORDS_CLN_TEXT +" TEXT NOT NULL, "
                + WORDS_CLN_NOTE +" TEXT, " + WORDS_CLN_IS_FAVORITE +" INTEGER NOT NULL, "
                + WORDS_CLN_COLOR +" INTEGER NOT NULL "+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ WORDS_TB_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertWord(Word word) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORDS_CLN_TEXT, word.getText());
        contentValues.put(WORDS_CLN_NOTE, word.getNote());
        if (word.isFavorite())
            contentValues.put(WORDS_CLN_IS_FAVORITE, 1);
        else contentValues.put(WORDS_CLN_IS_FAVORITE, 0);
        contentValues.put(WORDS_CLN_COLOR, word.getColor());
        long result= db.insert(WORDS_TB_NAME, null, contentValues);
        return result != -1;
    }

    public boolean deleteWord(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String args [] = {String.valueOf(id)};
        int result= db.delete(WORDS_TB_NAME, "id=?", args);
        return result > 0; // != 0
    }

    public ArrayList<Word> selectWords() {
        ArrayList<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + WORDS_TB_NAME + " ORDER BY " + WORDS_CLN_ID + " DESC", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_ID));
                String text = cursor.getString(cursor.getColumnIndex(WORDS_CLN_TEXT));
                String note = cursor.getString(cursor.getColumnIndex(WORDS_CLN_NOTE));
                boolean isFavorite = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_IS_FAVORITE)) == 1;
                int color = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_COLOR));
                words.add(new Word(id, text, note, isFavorite, color));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return words;
    }

    public ArrayList<Word> selectWordsIdASC() {
        ArrayList<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + WORDS_TB_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_ID));
                String text = cursor.getString(cursor.getColumnIndex(WORDS_CLN_TEXT));
                String note = cursor.getString(cursor.getColumnIndex(WORDS_CLN_NOTE));
                boolean isFavorite = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_IS_FAVORITE)) == 1;
                int color = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_COLOR));
                words.add(new Word(id, text, note, isFavorite, color));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return words;
    }

    public ArrayList<Word> selectWordsTextASC() {
        ArrayList<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + WORDS_TB_NAME + " ORDER BY " + WORDS_CLN_TEXT, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_ID));
                String text = cursor.getString(cursor.getColumnIndex(WORDS_CLN_TEXT));
                String note = cursor.getString(cursor.getColumnIndex(WORDS_CLN_NOTE));
                boolean isFavorite = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_IS_FAVORITE)) == 1;
                int color = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_COLOR));
                words.add(new Word(id, text, note, isFavorite, color));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return words;
    }

    public ArrayList<Word> selectWordsTextDESC() {
        ArrayList<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + WORDS_TB_NAME + " ORDER BY " + WORDS_CLN_TEXT + " DESC", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_ID));
                String text = cursor.getString(cursor.getColumnIndex(WORDS_CLN_TEXT));
                String note = cursor.getString(cursor.getColumnIndex(WORDS_CLN_NOTE));
                boolean isFavorite = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_IS_FAVORITE)) == 1;
                int color = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_COLOR));
                words.add(new Word(id, text, note, isFavorite, color));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return words;
    }

    public ArrayList<Word> selectWordsFavorite() {
        ArrayList<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+WORDS_TB_NAME+" WHERE " + WORDS_CLN_IS_FAVORITE+"= true", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_ID));
                String text = cursor.getString(cursor.getColumnIndex(WORDS_CLN_TEXT));
                String note = cursor.getString(cursor.getColumnIndex(WORDS_CLN_NOTE));
                boolean isFavorite = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_IS_FAVORITE)) == 1;
                int color = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_COLOR));
                words.add(new Word(id, text, note, isFavorite, color));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return words;
    }

    public ArrayList<Word> selectWordsSearch(String word) {
        ArrayList<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String args [] = {word};
        Cursor cursor = db.rawQuery("SELECT * FROM "+WORDS_TB_NAME+" WHERE " + WORDS_CLN_TEXT+" LIKE '%"+word+"%'", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_ID));
                String text = cursor.getString(cursor.getColumnIndex(WORDS_CLN_TEXT));
                String note = cursor.getString(cursor.getColumnIndex(WORDS_CLN_NOTE));
                boolean isFavorite = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_IS_FAVORITE)) == 1;
                int color = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_COLOR));
                words.add(new Word(id, text, note, isFavorite, color));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return words;
    }

    public ArrayList<Word> selectWordCat(ArrayList<String> arrayList) {
        ArrayList<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        for (String s : arrayList) {
            cursor = db.rawQuery("SELECT * FROM "+WORDS_TB_NAME+" WHERE " + WORDS_CLN_TEXT+" LIKE '"+s+"%'", null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_ID));
                    String text = cursor.getString(cursor.getColumnIndex(WORDS_CLN_TEXT));
                    String note = cursor.getString(cursor.getColumnIndex(WORDS_CLN_NOTE));
                    boolean isFavorite = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_IS_FAVORITE)) == 1;
                    int color = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_COLOR));
                    words.add(new Word(id, text, note, isFavorite, color));
                } while (cursor.moveToNext());
                cursor.close();
            }
        }
        return words;
    }

    public ArrayList<Word> selectWords(int search_id) {
        ArrayList<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+WORDS_TB_NAME+" WHERE " + WORDS_CLN_ID+"=?", new String[] {String.valueOf(search_id)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_ID));
                String text = cursor.getString(cursor.getColumnIndex(WORDS_CLN_TEXT));
                String note = cursor.getString(cursor.getColumnIndex(WORDS_CLN_NOTE));
                boolean isFavorite = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_IS_FAVORITE)) == 1;
                int color = cursor.getInt(cursor.getColumnIndex(WORDS_CLN_COLOR));
                words.add(new Word(id, text, note, isFavorite, color));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return words;
    }

    public boolean updateFavorite(int id, boolean isFavorite) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORDS_CLN_IS_FAVORITE, isFavorite);
        String args [] = {String.valueOf(id)};
        int result= db.update(WORDS_TB_NAME, contentValues, "id=?", args);
        return result != 0; // >0
    }

    public boolean updateWord(int id, String text, String note, int color) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORDS_CLN_TEXT, text);
        contentValues.put(WORDS_CLN_NOTE, note);
        contentValues.put(WORDS_CLN_COLOR, color);
        String args [] = {String.valueOf(id)};
        int result= db.update(WORDS_TB_NAME, contentValues, "id=?", args);
        return result != 0; // >0
    }
}
