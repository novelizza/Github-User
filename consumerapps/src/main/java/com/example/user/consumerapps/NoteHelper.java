package com.example.user.consumerapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.user.consumerapps.DatabaseContract.AccountColumns.TABLE_NAME;


/**
 * Created by sidiqpermana on 11/23/16.
 */

public class NoteHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static NoteHelper INSTANCE;

    private static SQLiteDatabase database;

    private NoteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static NoteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NoteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }


    /**
     * Ambil data dari semua note yang ada di dalam database
     *
     * @return cursor hasil queryAll
     */
    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }


    public Cursor queryById(String username) {
        return database.query(DATABASE_TABLE, null
                , "username = ?"
                , new String[]{username}
                , null
                , null
                , null
                , null);
    }

    /**
     * Simpan data ke dalam database
     *
     * @param values nilai data yang akan di simpan
     * @return long id dari data yang baru saja di masukkan
     */
    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    /**
     * Update data dalam database
     *
     * @param id     data dengan id berapa yang akan di update
     * @param values nilai data baru
     * @return int jumlah data yang ter-update
     */
    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteById(String username) {
        return database.delete(DATABASE_TABLE, "username = ?", new String[] { username });
    }
}
