package com.example.dam_project.data.model;

import android.provider.BaseColumns;

public class ProductContract {

    public static abstract class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME ="product";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String CATEGORY = "category";
        public static final String PRIZE = "prize";
        public static final String AVATAR_URI = "avatarUri";
    }

}
