package com.sasuke.imagify.util.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 5/3/2018.
 */

public class ImagifyDatabaseManager {

    //Table Name
    private static final String TABLE_NAME = "Queries";

    //Table Schema
    private static final String QUERY = "query";

    private static SQLiteDatabase sqLiteDatabase;

    public static void addQuery(ImagifyDatabaseAdapter databaseAdapter, String query) {
        if (databaseAdapter != null) {
            sqLiteDatabase = databaseAdapter.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(QUERY, query);

            sqLiteDatabase.insert(TABLE_NAME, null, values);
        }
    }

    public static List<String> getAllQueries(ImagifyDatabaseAdapter databaseAdapter) {
        List<String> queries = new ArrayList<>();
        if (databaseAdapter != null) {
            sqLiteDatabase = databaseAdapter.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{QUERY}, null,
                    null, null, null, null, null);
            if (cursor.getCount() > 0) {
                int queryIndex = cursor.getColumnIndex(QUERY);
                while (cursor.moveToNext()) {
                    queries.add(cursor.getString(queryIndex));
                }
            }

            cursor.close();
        }
        return queries;
    }

    public static boolean isQueryCached(ImagifyDatabaseAdapter databaseAdapter, String query) {
        boolean isQueryCached = false;
        if (databaseAdapter != null) {
            sqLiteDatabase = databaseAdapter.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{QUERY}, QUERY + " =? ",
                    new String[]{String.valueOf(query)}, null, null, null, null);

            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                isQueryCached = true;
            }
            cursor.close();
        }
        return isQueryCached;
    }
}
