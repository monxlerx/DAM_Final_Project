package com.example.dam_project.data.model;

import android.provider.BaseColumns;

public class WishContract {

    public static abstract class WishEntry implements BaseColumns {
        public static final String TABLE_NAME ="wish";

        public static final String WISH_ID = "wish_id";
        public static final String WISH_NAME = "wish_name";
        public static final String WISH_DESCRIPTION = "wish_description";
        public static final String WISH_CATEGORY = "wish_category";
        public static final String WISH_PRIZE = "wish_prize";
        public static final String WISH_AVATAR_URI = "wish_avatarUri";
        public static final String WISH_EMAIL = "wish_email";
    }
}
