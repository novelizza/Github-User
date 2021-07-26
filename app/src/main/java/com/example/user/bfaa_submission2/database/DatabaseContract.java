package com.example.user.bfaa_submission2.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dicoding on 10/12/2017.
 */

public class DatabaseContract {

    public static final class AccountColumns implements BaseColumns {
        public static final String AUTHORITY = "com.example.user.bfaa_submission2";
        private static final String SCHEME = "content";

        public static final String TABLE_NAME = "fav_user";

        //AccountEntity title
        public static final String USERNAME = "username";
        //AccountEntity description
        public static final String LINK = "link";
        //AccountEntity date
        public static final String AVATAR = "avatar";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();

    }
}
