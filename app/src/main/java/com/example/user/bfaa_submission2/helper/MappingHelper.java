package com.example.user.bfaa_submission2.helper;

import android.database.Cursor;

import com.example.user.bfaa_submission2.database.DatabaseContract;
import com.example.user.bfaa_submission2.entity.AccountEntity;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<AccountEntity> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<AccountEntity> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.AccountColumns._ID));
            String username = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.AccountColumns.USERNAME));
            String link = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.AccountColumns.LINK));
            String avatar = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.AccountColumns.AVATAR));
            notesList.add(new AccountEntity(id, username, link, avatar));
        }

        return notesList;
    }

    public static AccountEntity mapCursorToObject(Cursor notesCursor) {
        notesCursor.moveToFirst();
        int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.AccountColumns._ID));
        String username = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.AccountColumns.USERNAME));
        String link = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.AccountColumns.LINK));
        String avatar = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.AccountColumns.AVATAR));
        return new AccountEntity(id, username, link, avatar);
    }
}
